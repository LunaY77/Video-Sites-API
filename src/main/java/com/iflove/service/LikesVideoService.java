package com.iflove.service;

import com.iflove.entity.dto.LikesVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.mapper.LikesVideoMapper;
import jakarta.annotation.Resource;

/**
* @author IFLOVE
* @description 针对表【likes_video】的数据库操作Service
* @createDate 2024-09-04 08:28:29
*/
public interface LikesVideoService extends IService<LikesVideo> {
    void deleteAll();
}
