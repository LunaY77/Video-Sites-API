package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.iflove.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName video_comment
 */
@TableName(value ="video_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements BaseData {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long videoId;

    private Long parentId;

    private String content;

    private Long createdBy;

    private Date createdAt;

    private Date updatedAt;

    private Integer recommendCount;

    private Boolean isDeleted;

    public Comment(Long videoId, Long parentId, String content, Long createdBy, Date createdAt, Date updatedAt) {
        this.videoId = videoId;
        this.parentId = parentId;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}