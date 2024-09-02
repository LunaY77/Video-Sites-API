package com.iflove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iflove.entity.dto.Account;
import com.iflove.entity.dto.Role;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
* @author 苍镜月
* @description 针对表【account】的数据库操作Mapper
* @createDate 2024-08-30 13:42:18
* @Entity com.iflove.domain.Account
*/
public interface AccountMapper extends BaseMapper<Account> {

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "role_id", property = "roles", many =
                @Many(select = "getRolesById")
            )
    })
    @Select("select * from user_account where username = #{username} and is_deleted = 0")
    Account getUserByName(String username);

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "role_id", property = "roles", many =
                @Many(select = "getRolesById")
            )
    })
    @Select("select * from user_account where id = #{id} and is_deleted = 0")
    Account getUserById(Long id);

    @Select("select role from user_roles where id = #{id}")
    List<Role> getRolesById(String id);

    @Insert("insert into user_roles values (#{id}, #{role})")
    boolean saveRole(@Param("role") String role,
                     @Param("id") String id);
}




