package com.iflove.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
public class VideoInfoVO {
    private Long id;

    private String title;

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    private String filePath;

    private Long authorId;

    private Integer clickCount;

    private Integer recommendCount;

    private Integer commentCount;
}
