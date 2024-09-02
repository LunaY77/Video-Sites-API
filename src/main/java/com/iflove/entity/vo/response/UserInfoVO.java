package com.iflove.entity.vo.response;

import com.iflove.entity.dto.Role;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Data
public class UserInfoVO {
    private Long id;
    private String username;
    private List<Role> roles;
    private String avatarUrl;
    private Date createdAt;
    private Date updateAt;
}
