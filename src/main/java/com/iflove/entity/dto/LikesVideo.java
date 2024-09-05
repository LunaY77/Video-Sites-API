package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName likes_video
 */
@TableName(value ="likes_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesVideo {
    private Long id;

    private Long userId;

    private Long videoId;

    public LikesVideo(Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }
}