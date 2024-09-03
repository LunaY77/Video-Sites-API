package com.iflove.service;

import com.iflove.entity.dto.Videos;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.vo.request.VideoPostVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;

/**
* @author IFLOVE
* @description 针对表【video_videos】的数据库操作Service
* @createDate 2024-09-02 08:16:25
*/
public interface VideosService extends IService<Videos> {
    String publish(VideoPostVO vo, Long id);
    ListVO<VideoInfoVO> listVideo(String id, int pageNum, int pageSize);
    ListVO<VideoInfoVO> searchVideo(String keywords, Integer pageNum, Integer pageSize, Long fromDate, Long toDate, String username);
    Boolean addVideoComment(Long id);
}
