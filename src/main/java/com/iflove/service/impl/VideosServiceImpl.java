package com.iflove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.entity.dto.Followers;
import com.iflove.entity.dto.Videos;
import com.iflove.entity.vo.request.VideoPostVO;
import com.iflove.entity.vo.response.FollowInfoVO;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;
import com.iflove.service.AccountService;
import com.iflove.service.VideosService;
import com.iflove.mapper.VideosMapper;
import com.iflove.utils.FileConfig;
import com.iflove.utils.FileUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
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
    @Resource
    AccountService accountService;
    @Resource
    FileConfig fileConfig;

    @Override
    public String publish(VideoPostVO vo, Long id) {
        String contentType = vo.getFile().getContentType();
        // 检查文件格式是否正确
        if (!fileConfig.getAllowVideoTypes().contains(contentType)) {
            return "文件格式错误";
        }
        String path = fileUtil.saveFile(vo.getFile());
        if (Objects.isNull(path)) return "文件保存失败, 请检查文件类型";
        Videos video = new Videos(vo.getTitle(), vo.getDescription(), new Date(), new Date(), path, id);
        if (videosMapper.insert(video) == 0) {
            new File(path).delete();
            return "上传失败";
        }
        return null;
    }

    @Override
    public ListVO<VideoInfoVO> listVideo(String sid, int pageNum, int pageSize) {
        Long id = null;
        try {
            id = Long.valueOf(sid);
        } catch (NumberFormatException e) {
            return null;
        }
        Page<Videos> page = this.page(new Page<>(pageNum, pageSize), new QueryWrapper<Videos>().eq("author_id", id));

        return getListVO(page);
    }

    @Override
    public ListVO<VideoInfoVO> searchVideo(String keywords, Integer pageNum, Integer pageSize, Long fromDate, Long toDate, String username) {
        QueryWrapper<Videos> wrapper = new QueryWrapper<>();

        // 将 timestamp 转换为 Date 对象，然后比较
        if (fromDate != null && toDate != null) {
            Date fromDateObj = new Date(fromDate);
            Date toDateObj = new Date(toDate);
            wrapper.between("created_at", fromDateObj, toDateObj);
        } else if (fromDate != null) {
            Date fromDateObj = new Date(fromDate);
            wrapper.ge("created_at", fromDateObj);
        } else if (toDate != null) {
            Date toDateObj = new Date(toDate);
            wrapper.le("created_at", toDateObj);
        }

        // 模糊查询
        wrapper.and(w -> w
                .like(!keywords.isBlank(), "title", keywords)
                .or()
                .like(!keywords.isBlank(), "description", keywords));

        // username查询
        if (!username.isBlank()) {
            Long id = accountService.getUserByName(username).getId();
            if (id == null) return null;
            wrapper.and(w -> w.eq("author_id", id));
        }

        Page<Videos> page = this.page(new Page<>(pageNum, pageSize), wrapper);

        return getListVO(page);
    }

    @Override
    public Boolean addVideoComment(Long id) {
        Integer commentCount = this.query().eq("id", id).one().getCommentCount();
        if (commentCount != null) return this.update().eq("id", id).set("comment_count", commentCount + 1).update();
        return false;
    }

    private ListVO<VideoInfoVO> getListVO(Page<Videos> page) {
        List<Videos> records = page.getRecords();
        List<VideoInfoVO> items = records
                .stream()
                .map(videos -> videos.asViewObject(VideoInfoVO.class))
                .toList();
        Long total = page.getTotal();
        return new ListVO<>(items, total);
    }
}




