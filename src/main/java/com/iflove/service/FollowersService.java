package com.iflove.service;

import com.iflove.entity.dto.Followers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.vo.response.FollowingListVO;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Service
* @createDate 2024-09-02 08:18:18
*/
public interface FollowersService extends IService<Followers> {
    String subscribe(Long fromId, String toId, Integer type);
    FollowingListVO followingList(String id, Integer pageNum, Integer pageSize);
}
