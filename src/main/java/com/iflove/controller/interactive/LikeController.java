package com.iflove.controller.interactive;

import com.iflove.entity.RestBean;
import com.iflove.service.AccountService;
import com.iflove.service.LikeService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("api/like")
public class LikeController {
    @Resource
    AccountService accountService;
    @Resource
    LikeService likeService;

    @PostMapping("action")
    public RestBean<Void> action(@RequestParam(value = "video_id", required = false) String videoId,
                                 @RequestParam(value = "comment_id", required = false) String commentId,
                                 @RequestParam(value = "action_type")  Integer actionType) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = String.valueOf(accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId());
        return MessageHandler.stringMessageHandle(() ->
                likeService.action(userId, videoId, commentId, actionType));
    }
}
