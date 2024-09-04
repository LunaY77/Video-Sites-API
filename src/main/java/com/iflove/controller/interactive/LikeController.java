package com.iflove.controller.interactive;

import com.iflove.entity.RestBean;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;
import com.iflove.service.AccountService;
import com.iflove.service.LikeService;
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
@RequestMapping("api/like")
public class LikeController {
    @Resource
    AccountService accountService;
    @Resource
    LikeService likeService;

    /**
     * 点赞视频或评论
     * @param videoId 视频
     * @param commentId 评论
     * @param actionType 操作类型 （ 1 点赞 / 0 取消点赞）
     * @return 结果集
     */
    @PostMapping("action")
    public RestBean<Void> action(@RequestParam(value = "video_id", required = false) String videoId,
                                 @RequestParam(value = "comment_id", required = false) String commentId,
                                 @RequestParam(value = "action_type")  Integer actionType) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = String.valueOf(accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId());
        return MessageHandler.stringMessageHandle(() ->
                likeService.action(userId, videoId, commentId, actionType));
    }

    /**
     * 返回用户的点赞列表
     * @param userId 用户
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @GetMapping("list")
    public RestBean<ListVO<VideoInfoVO>> likeList(@RequestParam(value = "user_id", required = false) String userId,
                                                  @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                  @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                likeService.likeList(userId, pageNum, pageSize));
    }
}
