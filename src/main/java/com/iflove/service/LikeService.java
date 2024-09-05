package com.iflove.service;

import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
public interface LikeService {
    String action(String userId, String videoSid, String commentSid, Integer actionType);
    ListVO<VideoInfoVO> likeList(String userId, int pageNum, int pageSize);
    void transLikeFromRedis2DB();
    void transCountValueFromRedis2DB();
    void transLikeFromDB2Redis();
    void transCountValueFromDB2Redis();
}
