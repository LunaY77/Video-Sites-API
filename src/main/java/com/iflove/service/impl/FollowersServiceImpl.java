package com.iflove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Followers;
import com.iflove.service.FollowersService;
import com.iflove.mapper.FollowersMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Service实现
* @createDate 2024-09-02 08:18:18
*/
@Service
public class FollowersServiceImpl extends ServiceImpl<FollowersMapper, Followers> implements FollowersService{

    /**
     * 关注或取消关注
     * @param fromId 关注者
     * @param toId 被关注的
     * @param type 1 代表关注，0 代表取关
     * @return null 成功
     */
    @Override
    public String subscribe(Long fromId, Long toId, Integer type) {
        Followers one = this.query().eq("follower_id", fromId).eq("following_id", toId).one();
        if (Objects.isNull(one)) {
            boolean save = this.save(new Followers(fromId, toId, new Date(), type));
            return save ? null : "操作失败";
        }
        // 不可重复操作
        if ((one.getStatus() ^ type) == 0) return "请勿重复操作";
        boolean update = this.update()
                .eq("follower_id", fromId)
                .eq("following_id", toId)
                .set("status", one.getStatus() ^ 1)
                .set("follow_time", new Date())
                .update();
        return update ? null : "操作失败";
    }


}




