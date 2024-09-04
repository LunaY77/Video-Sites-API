package com.iflove.service;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public interface LikeService {
    String action(String userId, String videoSid, String commentSid, Integer actionType);
    void transLikeFromRedis2DB();
    void transVideoLikeCountFromRedis2DB();
    void transCommentLikeCountFromRedis2DB();
    void transClickCountFromRedis2DB();
}
