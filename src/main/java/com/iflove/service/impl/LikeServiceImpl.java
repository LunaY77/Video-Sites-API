package com.iflove.service.impl;

import com.iflove.entity.Const;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;
import com.iflove.service.LikeService;
import com.iflove.service.VideosService;
import com.iflove.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    RedisUtil redisUtil;
    @Resource
    VideosService videosService;

    /**
     * 点赞视频或评论
     * @param userId 用户
     * @param videoSid 视频
     * @param commentSid 评论
     * @param actionType 操作类型 （ 1 点赞 / 0 取消点赞）
     * @return 成功 null / 失败 错误信息
     */
    @Override
    public String action(String userId, String videoSid, String commentSid, Integer actionType) {
        if (actionType != 0 && actionType != 1) return "未知操作";
        if (videoSid.isBlank() == commentSid.isBlank()) return "未知点赞对象";
        Long videoId = null, commentId = null;
        try {
            videoId = videoSid.isBlank() ? null : Long.parseLong(videoSid);
            commentId = commentSid.isBlank() ? null : Long.parseLong(commentSid);
        } catch (NumberFormatException e) {
            return "id格式错误";
        }
        // 视频
        if (videoId != null) {
            // 判断是否已经点赞，避免重复操作
            int like = redisUtil.hGetLike(Const.VIDEO_USER_LIKED, userId, videoSid);
            if ((like ^ actionType) == 0) return "请勿重复操作";
            // 存储点赞信息
            redisUtil.hSaveLike(Const.VIDEO_USER_LIKED, userId, videoSid, actionType);
            redisUtil.zsIncr(Const.VIDEO_RECOMMEND_COUNT, videoSid, actionType == 0 ? -1 : 1);
            return null;
        }
        // 评论
        int like = redisUtil.hGetLike(Const.COMMENT_USER_LIKED, userId, commentSid);
        if ((like ^ actionType) == 0) return "请勿重复操作";
        // 存储点赞信息
        redisUtil.hSaveLike(Const.COMMENT_USER_LIKED, userId, commentSid, actionType);
        redisUtil.zsIncr(Const.COMMENT_RECOMMEND_COUNT, commentSid, actionType == 0 ? -1 : 1);
        return null;
    }

    /**
     * 返回用户的点赞列表
     * @param userId 用户
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @Override
    public ListVO<VideoInfoVO> likeList(String userId, int pageNum, int pageSize) {
        List<Long> videoIds = redisUtil.hGetLikeList(Const.VIDEO_USER_LIKED, userId);
        System.out.println(videoIds);
        // 获取分页初始值
        int start = pageNum * pageSize;
        // 分页查询
        List<VideoInfoVO> items = new ArrayList<>();
        for (int i = start; i < videoIds.size() && i < start + pageSize; i++) {
            long videoId = videoIds.get(i);
            items.add(
                    videosService
                            .query()
                            .eq("id", videoId)
                            .eq("is_deleted", 0)
                            .one()
                            .asViewObject(VideoInfoVO.class, v -> {
                                v.setClickCount(redisUtil.zsScore(Const.VIDEO_CLICK_COUNT, String.valueOf(videoId)));
                                v.setRecommendCount(redisUtil.zsScore(Const.VIDEO_RECOMMEND_COUNT, String.valueOf(videoId)));
                            })
            );
        }
        return new ListVO<>(items, (long) videoIds.size());
    }


    // 持久化存储
    @Override
    public void transLikeFromRedis2DB() {

    }

    @Override
    public void transVideoLikeCountFromRedis2DB() {

    }

    @Override
    public void transCommentLikeCountFromRedis2DB() {

    }

    @Override
    public void transClickCountFromRedis2DB() {

    }


}
