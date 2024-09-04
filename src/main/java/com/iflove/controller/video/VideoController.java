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

    /**
     * 根据id查询发布视频列表
     * @param id 对象
     * @param pageNum 当前页，默认0
     * @param pageSize 大小，默认10
     * @return 结果集
     */
    @GetMapping("list")
    public RestBean<ListVO<VideoInfoVO>> listVideo(@RequestParam("user_id") String id,
                                                   @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                videosService.listVideo(id, pageNum, pageSize));
    }

    /**
     * 搜索视频
     * @param keywords 关键字
     * @param pageNum 当前页，默认0
     * @param pageSize 大小，默认10
     * @param fromDate 起始日期
     * @param toDate 截止日期
     * @param username 发布人
     * @return 结果集
     */
    @PostMapping("search")
    public RestBean<ListVO<VideoInfoVO>> searchVideo(@RequestParam(value = "keywords", required = false) String keywords,
                                                     @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                     @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "from_date", required = false) Long fromDate,
                                                     @RequestParam(value = "to_date", required = false) Long toDate,
                                                     @RequestParam(value = "username", required = false) String username) {
        return MessageHandler.messageHandle(() ->
                videosService.searchVideo(keywords, pageNum, pageSize, fromDate, toDate, username));
    }

    /**
     * 浏览视频，增加点击量
     * @param id id
     * @return 结果集
     */
    @GetMapping("browse")
    public RestBean<VideoInfoVO> browse(@RequestParam("video_id") String id) {
        return MessageHandler.messageHandle(id, videosService::browse);
    }

    /**
     * 根据点击量降序排序
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @GetMapping("clickRank")
    public RestBean<ListVO<VideoInfoVO>> clickRank( @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                  @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                videosService.popular(pageNum, pageSize, true));
    }

    /**
     * 根据点赞量降序排序
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @GetMapping("recommendRank")
    public RestBean<ListVO<VideoInfoVO>> recommendRank( @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
                                                    @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        return MessageHandler.messageHandle(() ->
                videosService.popular(pageNum, pageSize, false));
    }
}
