package com.iflove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Likes;
import com.iflove.service.LikesService;
import com.iflove.mapper.LikesMapper;
import org.springframework.stereotype.Service;

/**
* @author IFLOVE
* @description 针对表【social_likes】的数据库操作Service实现
* @createDate 2024-09-02 08:18:18
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

}




