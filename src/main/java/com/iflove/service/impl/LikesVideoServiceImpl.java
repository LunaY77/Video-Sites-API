package com.iflove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.LikesVideo;
import com.iflove.service.LikesVideoService;
import com.iflove.mapper.LikesVideoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
* @author IFLOVE
* @description 针对表【likes_video】的数据库操作Service实现
* @createDate 2024-09-04 08:28:29
*/
@Service
public class LikesVideoServiceImpl extends ServiceImpl<LikesVideoMapper, LikesVideo> implements LikesVideoService{
    @Resource
    LikesVideoMapper mapper;

    @Override
    public void deleteAll() {
        mapper.delete(new QueryWrapper<>());
    }
}




