package com.iflove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Followers;
import com.iflove.service.FollowersService;
import com.iflove.mapper.FollowersMapper;
import org.springframework.stereotype.Service;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Service实现
* @createDate 2024-09-02 08:18:18
*/
@Service
public class FollowersServiceImpl extends ServiceImpl<FollowersMapper, Followers>
    implements FollowersService{

}




