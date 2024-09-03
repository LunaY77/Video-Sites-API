package com.iflove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Comment;
import com.iflove.entity.vo.response.CommentInfoVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.service.CommentService;
import com.iflove.mapper.CommentMapper;
import com.iflove.service.VideosService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author IFLOVE
* @description 针对表【video_comment】的数据库操作Service实现
* @createDate 2024-09-02 08:16:25
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{
    @Resource
    CommentMapper commentMapper;
    @Resource
    VideosService videosService;

    /**
     * 发表评论
     * @param id 发布人
     * @param videoSid 视频id
     * @param commentSid 评论id
     * @param content 内容
     * @return 成功 null / 失败 错误信息
     */
    @Transactional
    @Override
    public String publish(Long id, String videoSid, String commentSid, String content) {
        if (videoSid.isBlank() == commentSid.isBlank()) return "未知评论对象";
        Long videoId = null, commentId = null;
        try {
            videoId = videoSid.isBlank() ? null : Long.parseLong(videoSid);
            commentId = commentSid.isBlank() ? null : Long.parseLong(commentSid);
        } catch (NumberFormatException e) {
            return "id格式错误";
        }
        Comment comment = new Comment(videoId, commentId, content, id, new Date(), new Date());
        // 对视频评论
        if (videoId != null) {
            boolean save = this.save(comment) && videosService.addVideoComment(videoId);
            return save ? null : "评论失败";
        }
        // 对评论的评论
        Long belongVideoId = this.query().eq("id", commentId).one().getVideoId();
        if (belongVideoId == null) return "评论失败";
        comment.setVideoId(belongVideoId);
        boolean save = this.save(comment) && videosService.addVideoComment(belongVideoId);
        return save ? null : "评论失败";
    }

    /**
     * 评论列表
     * @param videoSid 视频id
     * @param commentSid 评论id
     * @param pageNum 页码
     * @param pageSize 大小
     * @return vo
     */
    @Override
    public ListVO<CommentInfoVO> listComment(String videoSid, String commentSid, Integer pageNum, Integer pageSize) {
        if (videoSid.isBlank() == commentSid.isBlank()) return null;
        Long videoId = null, commentId = null;
        try {
            videoId = videoSid.isBlank() ? null : Long.parseLong(videoSid);
            commentId = commentSid.isBlank() ? null : Long.parseLong(commentSid);
        } catch (NumberFormatException e) {
            return null;
        }
        // 找到父评论
        Page<Comment> page = null;
        if (videoId != null) {
            page = this.page(new Page<>(pageNum, pageSize),
                    new QueryWrapper<Comment>()
                            .eq("video_id", videoId)
                            .eq("parent_id", -1)
                            .eq("is_deleted", 0));
        } else {
            page = this.page(new Page<>(pageNum, pageSize),
                    new QueryWrapper<Comment>()
                            .eq("parent_id", commentId)
                            .eq("is_deleted", 0));
        }

        List<Comment> records = page.getRecords();
        // 将结果映射为vo对象，并 dfs 子评论数量
        List<CommentInfoVO> results = records
                .stream()
                .map(comment -> {
                    long childCount = dfs(comment.getId());
                    return comment.asViewObject(CommentInfoVO.class, v -> v.setChildCount(childCount));
                })
                .toList();
        return new ListVO<>(results, page.getTotal());
    }

    /**
     * 简单dfs，通过parentId查找子评论数量
     * @param parentId 父评论id
     * @return 子评论数量
     */
    private long dfs(Long parentId) {
        long result = 0;
        List<Comment> childs = this.query().eq("parent_id", parentId).eq("is_deleted", 0).list();
        if (childs == null) return result;
        for (Comment child : childs) {
            result += dfs(child.getId());
        }
        return result + childs.size();
    }
}




