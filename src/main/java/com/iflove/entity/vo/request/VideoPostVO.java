package com.iflove.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
@AllArgsConstructor
public class VideoPostVO {
    private MultipartFile file;
    private String title;
    private String description;
}
