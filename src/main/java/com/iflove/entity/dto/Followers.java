package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName social_followers
 */
@TableName(value ="social_followers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Followers {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long followerId;

    private Long followingId;

    private Date followTime;

    private Integer status;

    public Followers(Long followerId, Long followingId, Date followTime, Integer status) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.followTime = followTime;
        this.status = status;
    }
}