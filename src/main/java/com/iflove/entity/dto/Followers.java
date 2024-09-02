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
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private Integer followerId;

    private Integer followingId;

    private Date followTime;
}