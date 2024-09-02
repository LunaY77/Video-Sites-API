package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName social_followers
 */
@TableName(value ="social_followers")
@Data
public class Followers {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long followerId;

    private Long followingId;

    private Date followTime;
}