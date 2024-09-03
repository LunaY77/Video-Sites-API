package com.iflove.service;

import com.iflove.entity.dto.Followers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.vo.response.FollowInfoVO;
import com.iflove.entity.vo.response.ListVO;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Service
* @createDate 2024-09-02 08:18:18
*/
public interface FollowersService extends IService<Followers> {
    String subscribe(Long fromId, String toId, Integer type);
    ListVO<FollowInfoVO> followerList(String id, Integer pageNum, Integer pageSize);
    ListVO<FollowInfoVO> followingList(String id, Integer pageNum, Integer pageSize);
    ListVO<FollowInfoVO> friendsList(Long id, Integer pageNum, Integer pageSize);
}
