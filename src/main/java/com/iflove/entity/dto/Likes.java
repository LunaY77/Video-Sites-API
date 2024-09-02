package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName social_likes
 */
@TableName(value ="social_likes")
@Data
public class Likes {
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private Integer userId;

    private Integer videoId;
}