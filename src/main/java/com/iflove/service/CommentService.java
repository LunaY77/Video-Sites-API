package com.iflove.service;

import com.iflove.entity.dto.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.vo.response.CommentInfoVO;
import com.iflove.entity.vo.response.ListVO;

/**
* @author IFLOVE
* @description 针对表【video_comment】的数据库操作Service
* @createDate 2024-09-02 08:16:25
*/
public interface CommentService extends IService<Comment> {
    String publish(Long id, String videoId, String commentId, String content);
    ListVO<CommentInfoVO> listComment(String videoSid, String commentSid, Integer pageNum, Integer pageSize);
    String delete(String videoId, String commentId);
}
