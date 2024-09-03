package com.iflove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Followers;
import com.iflove.entity.vo.response.FollowInfoVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.service.AccountService;
import com.iflove.service.FollowersService;
import com.iflove.mapper.FollowersMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author IFLOVE
* @description 针对表【social_followers】的数据库操作Service实现
* @createDate 2024-09-02 08:18:18
*/
@Service
public class FollowersServiceImpl extends ServiceImpl<FollowersMapper, Followers> implements FollowersService{

    @Resource
    AccountService accountService;
    @Resource
    FollowersMapper followersMapper;

    /**
     * 关注或取消关注
     * @param fromId 关注者
     * @param to 被关注的
     * @param type 1 代表关注，0 代表取关
     * @return null 成功
     */
    @Override
    public String subscribe(Long fromId, String to, Integer type) {
        Long toId = 0L;
        try {
            toId = Long.valueOf(to);
        } catch (NumberFormatException e) {
            return e.getMessage();
        }
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

    /**
     * 使用内连接查询朋友列表
     * @param id 当前用户id
     * @param pageNum 当前页
     * @param pageSize 大小
     * @return vo
     */
    @Override
    public ListVO<FollowInfoVO> friendsList(Long id, Integer pageNum, Integer pageSize) {
        Page<Followers> page = followersMapper.friendsList(new Page<>(pageNum, pageSize), id);

        List<Followers> records = page.getRecords();
        List<FollowInfoVO> items = records
                .stream()
                .map(follower -> accountService
                        .getUserById(follower.getFollowingId())
                        .asViewObject(FollowInfoVO.class))
                .toList();
        long total = page.getTotal();
        return new ListVO<>(items, total);
    }

    /**
     * 分页查询粉丝列表
     * @param id id
     * @param pageNum 当前页
     * @param pageSize 大小
     * @return vo
     */
    @Override
    public ListVO<FollowInfoVO> followerList(String id, Integer pageNum, Integer pageSize) {
        Long followerId = null;
        try {
            followerId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
        return followList(null, followerId, pageNum, pageSize);
    }

    /**
     * 分页查询关注列表
     * @param id id
     * @param pageNum 当前页
     * @param pageSize 大小
     * @return vo
     */
    @Override
    public ListVO<FollowInfoVO> followingList(String id, Integer pageNum, Integer pageSize) {
        Long followingId = null;
        try {
            followingId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return null;
        }
        return followList(followingId, null, pageNum, pageSize);
    }

    /**
     * 分页查询关注/粉丝列表
     * @param followerId 粉丝id
     * @param followingId up主id
     * @param pageNum 当前页
     * @param pageSize 大小
     * @return vo
     */
    private ListVO<FollowInfoVO> followList(Long followerId, Long followingId, Integer pageNum, Integer pageSize) {
        Page<Followers> page = this.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Followers>()
                        .eq(followerId != null, "follower_id", followerId)
                        .eq(followingId != null, "following_id", followingId)
                        .eq("status", 1));

        List<Followers> records = page.getRecords();
        List<FollowInfoVO> items = records
                .stream()
                .map(follower -> accountService
                        .getUserById(Objects.nonNull(followerId) ? follower.getFollowingId() : follower.getFollowerId())
                        .asViewObject(FollowInfoVO.class))
                .toList();
        long total = page.getTotal();
        return new ListVO<>(items, total);
    }
}




