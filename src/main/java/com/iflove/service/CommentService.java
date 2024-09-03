package com.iflove.service;

import com.iflove.entity.dto.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author IFLOVE
* @description 针对表【video_comment】的数据库操作Service
* @createDate 2024-09-02 08:16:25
*/
public interface CommentService extends IService<Comment> {
    String publish(Long id, String videoId, String commentId, String content);
}
