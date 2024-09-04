package com.iflove.service.impl;

import com.iflove.entity.Const;
import com.iflove.service.LikeService;
import com.iflove.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    RedisUtil redisUtil;

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
