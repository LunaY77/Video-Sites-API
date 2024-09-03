package com.iflove.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iflove.entity.dto.Followers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Mapper
* @createDate 2024-09-02 08:18:18
* @Entity com.iflove.entity.dto.Followers
*/
public interface FollowersMapper extends BaseMapper<Followers> {

    @Select("select a.following_id from social_followers a " +
            "inner join (select * from social_followers where status = 1) b on a.follower_id = b.following_id and a.following_id = b.follower_id " +
            "where a.follower_id = #{id} and a.status = 1")
    Page<Followers> friendsList(Page<Followers> page, Long id);
}




