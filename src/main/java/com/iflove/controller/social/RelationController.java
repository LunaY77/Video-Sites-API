package com.iflove.controller.social;

import com.iflove.entity.RestBean;
import com.iflove.entity.vo.response.FollowInfoVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.service.AccountService;
import com.iflove.service.FollowersService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("api/relation")
public class RelationController {
    @Resource
    AccountService accountService;
    @Resource
    FollowersService followersService;

    /**
     * 关注/取关
     * @param toId 对象
     * @param type 1 关注 / 0 取关
     * @return 结果集
     */
    @PostMapping("subscribe")
    public RestBean<Void> subscribe(@RequestParam("to_user_id") String toId,
                                    @RequestParam("type") Integer type) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long fromId = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.stringMessageHandle(() ->
                followersService.subscribe(fromId, toId, type));
    }

    /**
     * 关注列表
     * @param id 对象
     * @param pageNum 当前页，默认0
     * @param pageSize 大小，默认10
     * @return 结果集
     */
    @GetMapping("following/list")
    public RestBean<ListVO<FollowInfoVO>> followingList(@RequestParam("user_id") String id,
                                                @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                followersService.followingList(id, pageNum, pageSize));
    }

    /**
     * 粉丝列表
     * @param id 对象
     * @param pageNum 当前页，默认0
     * @param pageSize 大小，默认10
     * @return 结果集
     */
    @GetMapping("follower/list")
    public RestBean<ListVO<FollowInfoVO>> followerList(@RequestParam("user_id") String id,
                                                       @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                followersService.followerList(id, pageNum, pageSize));
    }

    /**
     * 朋友列表
     * @param pageNum 当前页，默认0
     * @param pageSize 大小，默认10
     * @return 结果集
     */
    @GetMapping("friends/list")
    public RestBean<ListVO<FollowInfoVO>> friendsList(@RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.messageHandle(() ->
                followersService.friendsList(id, pageNum, pageSize));
    }
}
