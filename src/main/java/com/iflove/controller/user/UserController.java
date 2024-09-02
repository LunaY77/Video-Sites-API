package com.iflove.controller.user;

import com.iflove.entity.RestBean;
import com.iflove.entity.ResultCodeEnum;
import com.iflove.entity.dto.Account;
import com.iflove.entity.vo.response.UserInfoVO;
import com.iflove.service.AccountService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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

    @GetMapping("info")
    public RestBean<UserInfoVO> getUserInfo(@RequestParam("id") String id) {
        return MessageHandler.messageHandle(id, accountService::getUserInfoById);
    }

    @PutMapping("avatar/upload")
    public RestBean<UserInfoVO> uploadAvatar(MultipartFile file) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return MessageHandler.messageHandle(() ->
                accountService.saveUserAvatar(file, username));
    }
}
