package com.iflove.controller.social;

import com.iflove.entity.RestBean;
import com.iflove.entity.vo.response.FollowListVO;
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

    @PostMapping("subscribe")
    public RestBean<Void> subscribe(@RequestParam("to_user_id") String toId,
                                    @RequestParam("type") Integer type) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long fromId = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.stringMessageHandle(() ->
                followersService.subscribe(fromId, toId, type));
    }

    @GetMapping("following/list")
    public RestBean<FollowListVO> followingList(@RequestParam("user_id") String id,
                                                @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                followersService.followingList(id, pageNum, pageSize));
    }

    @GetMapping("follower/list")
    public RestBean<FollowListVO> followerList(@RequestParam("user_id") String id,
                                               @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                followersService.followerList(id, pageNum, pageSize));
    }

    @GetMapping("friends/list")
    public RestBean<FollowListVO> friendsList(@RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                               @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.messageHandle(() ->
                followersService.friendsList(id, pageNum, pageSize));
    }
}
