package com.iflove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Comment;
import com.iflove.service.CommentService;
import com.iflove.mapper.CommentMapper;
import com.iflove.service.VideosService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}




