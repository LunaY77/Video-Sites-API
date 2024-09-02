package com.iflove.videositesapi;

import com.iflove.entity.dto.Role;
import com.iflove.entity.vo.response.UserInfoVO;
import com.iflove.utils.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class VideoSitesApiApplicationTests {

    @Test
    void contextLoads() {
        UserInfoVO vo = new UserInfoVO();
        vo.setId(123L);
        vo.setUsername("se");
        vo.setRoles(List.of(new Role("user")));
        vo.setUpdateAt(new Date());
        vo.setCreatedAt(new Date());
        vo.setAvatarUrl("");
        System.out.println(JacksonUtil.obj2String(vo));
    }

}
