package com.iflove.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Videos;
import com.iflove.entity.vo.request.VideoPostVO;
import com.iflove.service.VideosService;
import com.iflove.mapper.VideosMapper;
import com.iflove.utils.FileUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
* @author IFLOVE
* @description 针对表【video_videos】的数据库操作Service实现
* @createDate 2024-09-02 08:16:25
*/
@Service
public class VideosServiceImpl extends ServiceImpl<VideosMapper, Videos> implements VideosService{
    @Resource
    FileUtil fileUtil;
    @Resource
    VideosMapper videosMapper;

    @Override
    public String publish(VideoPostVO vo, Long id) {
        String path = fileUtil.saveFile(vo.getFile());
        if (Objects.isNull(path)) return "文件保存失败, 请检查文件类型";
        Videos video = new Videos(vo.getTitle(), vo.getDescription(), new Date(), new Date(), path, id);
        if (videosMapper.insert(video) == 0) {
            new File(path).delete();
            return "上传失败";
        }
        return null;
    }
}




