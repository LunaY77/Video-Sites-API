package com.iflove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Comment;
import com.iflove.service.CommentService;
import com.iflove.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author IFLOVE
* @description 针对表【video_comment】的数据库操作Service实现
* @createDate 2024-09-02 08:16:25
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{

}




