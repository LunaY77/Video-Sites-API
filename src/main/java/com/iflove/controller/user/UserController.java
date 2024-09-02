package com.iflove.controller.user;

import com.iflove.entity.RestBean;
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
//    public RestBean<> getUserInfo(@RequestParam("id") int id) {
//        return
//    }
}
