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
 * @TableName likes_comment
 */
@TableName(value ="likes_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesComment {
    private Long id;

    private Long userId;

    private Long commentId;

    public LikesComment(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}