package com.iflove.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
}
