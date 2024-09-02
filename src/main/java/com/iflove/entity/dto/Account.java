package com.iflove.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iflove.entity.BaseData;
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
@TableName(value ="user_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements BaseData {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String password;

    private String email;

    private Date createdAt;

    private Date updateAt;

    private String avatarUrl;

    private String roleId;

    private Boolean isDeleted;

    private List<Role> roles;

    public Account(String username, String password, String email, Date createdAt, Date updateAt, String roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.roleId = roleId;
    }
}