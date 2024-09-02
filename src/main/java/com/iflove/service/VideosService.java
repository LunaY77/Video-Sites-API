package com.iflove.service;

import com.iflove.entity.dto.Videos;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.vo.request.VideoPostVO;

/**
* @author IFLOVE
* @description 针对表【video_videos】的数据库操作Service
* @createDate 2024-09-02 08:16:25
*/
public interface VideosService extends IService<Videos> {
    String publish(VideoPostVO vo, Long id);
}
