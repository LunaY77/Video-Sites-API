package com.iflove.controller.social;

import com.iflove.entity.RestBean;
import com.iflove.service.AccountService;
import com.iflove.service.FollowersService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RelationService;
import java.util.Optional;

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
                followersService.subscribe(fromId, Long.valueOf(toId), type));
    }
}
