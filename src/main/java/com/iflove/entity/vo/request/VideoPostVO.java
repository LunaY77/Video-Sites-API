package com.iflove.entity.vo.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
public class VideoPostVO {
    private MultipartFile file;
    private String title;
    private String description;
}
