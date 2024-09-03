package com.iflove.controller.video;

import com.iflove.entity.RestBean;
import com.iflove.entity.vo.request.VideoPostVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;
import com.iflove.service.AccountService;
import com.iflove.service.VideosService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("api/video")
public class VideoController {
    @Resource
    VideosService videosService;
    @Resource
    AccountService accountService;

    /**
     * 投稿视频
     * @param file 视频文件
     * @param title 标题
     * @param description 描述
     * @return 响应消息
     */
    @PostMapping("publish")
    public RestBean<Void> publish(@RequestParam("file") MultipartFile file,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.stringMessageHandle(() ->
                videosService.publish(new VideoPostVO(file, title, description), id));
    }

    @GetMapping("list")
    public RestBean<ListVO<VideoInfoVO>> listVideo(@RequestParam("user_id") String id,
                                                   @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                videosService.listVideo(id, pageNum, pageSize));
    }

    @PostMapping("search")
    public RestBean<ListVO<VideoInfoVO>> searchVideo(@RequestParam(value = "keywords", required = false) String keywords,
                                                     @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                     @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "from_date", required = false) Integer fromDate,
                                                     @RequestParam(value = "to_date", required = false) Integer toDate,
                                                     @RequestParam(value = "username", required = false) String username) {

    }
}
