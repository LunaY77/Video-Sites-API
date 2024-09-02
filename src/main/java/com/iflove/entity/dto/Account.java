package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@TableName(value ="account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Date createdAt;

    private Date updateAt;

    private String avatarUrl;

    private Boolean isDeleted;

    private List<Role> roles;
}