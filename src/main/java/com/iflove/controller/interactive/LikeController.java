package com.iflove.controller.interactive;

import com.iflove.entity.RestBean;
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

    @PostMapping("action")
    public RestBean<Void> action(@RequestParam(value = "video_id", required = false) String videoId,
                                 @RequestParam(value = "comment_id", required = false) String commentId,
                                 @RequestParam(value = "action_type") Integer actionType) {
        return null;
    }
}
