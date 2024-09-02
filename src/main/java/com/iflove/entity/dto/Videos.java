package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName video_videos
 */
@TableName(value ="video_videos")
@Data
public class Videos {
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private String title;

    private String description;

    private Date createdAt;

    private Date updatedAt;

    private String filePath;

    private Integer authorId;

    private Integer clickCount;

    private Integer recommendCount;

    private Integer commentCount;

    private Integer isDeleted;
}