package com.iflove.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 多了个childCount
 */
@Data
public class CommentInfoVO {
    private Long id;

    private Long videoId;

    private Long parentId;

    private String content;

    private Long createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    private Integer recommendCount;

    private Boolean isDeleted;

    private Long childCount;
}
