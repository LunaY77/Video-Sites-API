package com.iflove.mapper;

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
}




