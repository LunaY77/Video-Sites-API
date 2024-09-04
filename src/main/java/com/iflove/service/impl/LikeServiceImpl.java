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
    public String action(String videoSid, String commentSid, Integer actionType) {
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
            // 判断点赞还是取消点赞
            int delta = actionType == 1 ? 1 : -1;

            redisUtil.zsIncr(Const.VIDEO_RECOMMEND_COUNT, videoSid, delta);

        }

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
