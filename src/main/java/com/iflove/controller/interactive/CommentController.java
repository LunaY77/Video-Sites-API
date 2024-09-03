package com.iflove.controller.interactive;

import com.iflove.entity.RestBean;
import com.iflove.service.AccountService;
import com.iflove.service.CommentService;
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
@RequestMapping("api/comment")
public class CommentController {
    @Resource
    CommentService commentService;
    @Resource
    AccountService accountService;

    @PostMapping("publish")
    public RestBean<Void> publish(@RequestParam(value = "video_id", required = false) String videoId,
                                  @RequestParam(value = "comment_id", required = false) String commentId,
                                  @RequestParam(value = "content") String content) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.stringMessageHandle(() ->
                commentService.publish(id, videoId, commentId, content));
    }


}
