package com.iflove.controller.user;

import com.iflove.entity.RestBean;
import com.iflove.entity.dto.Account;
import com.iflove.entity.vo.response.UserInfoVO;
import com.iflove.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    @Resource
    AccountService accountService;

//    @GetMapping("info")
//    public RestBean<UserInfoVO> getUserInfo(@RequestParam("id") String id) {
//
//    }
}
