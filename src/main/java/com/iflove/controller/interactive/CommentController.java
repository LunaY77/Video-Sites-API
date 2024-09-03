package com.iflove.controller.interactive;

import com.iflove.entity.RestBean;
import com.iflove.entity.vo.response.CommentInfoVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.service.AccountService;
import com.iflove.service.CommentService;
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
@RequestMapping("api/comment")
public class CommentController {
    @Resource
    CommentService commentService;
    @Resource
    AccountService accountService;

    /**
     * 发表评论
     * @param videoId 视频id
     * @param commentId 评论id
     * @param content 内容
     * @return 结果集
     */
    @PostMapping("publish")
    public RestBean<Void> publish(@RequestParam(value = "video_id", required = false) String videoId,
                                  @RequestParam(value = "comment_id", required = false) String commentId,
                                  @RequestParam(value = "content") String content) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.stringMessageHandle(() ->
                commentService.publish(id, videoId, commentId, content));
    }

    /**
     * 评论列表
     * @param videoId 视频id
     * @param commentId 评论id
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @GetMapping("list")
    public RestBean<ListVO<CommentInfoVO>> listComment(@RequestParam(value = "video_id", required = false) String videoId,
                                                       @RequestParam(value = "comment_id", required = false) String commentId,
                                                       @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                       @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                commentService.listComment(videoId, commentId, pageNum, pageSize));
    }

    /**
     * 删除评论及其子评论
     * @param videoId 视频id
     * @param commentId 评论id
     * @return 结果集
     */
    @DeleteMapping("delete")
    public RestBean<Void> delete(@RequestParam(value = "video_id", required = false) String videoId,
                                 @RequestParam(value = "comment_id", required = false) String commentId) {
        return MessageHandler.stringMessageHandle(() ->
                commentService.delete(videoId, commentId));
    }
}
