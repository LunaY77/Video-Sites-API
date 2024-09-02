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
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private Integer videoId;

    private Integer parentId;

    private String content;

    private Integer createdBy;

    private Date createdAt;

    private Date updatedAt;

    private Integer isDeleted;

}