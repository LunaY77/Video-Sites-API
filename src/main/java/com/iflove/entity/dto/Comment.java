package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName video_comment
 */
@TableName(value ="video_comment")
@Data
public class Comment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long videoId;

    private Long parentId;

    private String content;

    private Long createdBy;

    private Date createdAt;

    private Date updatedAt;

    private Boolean isDeleted;

}