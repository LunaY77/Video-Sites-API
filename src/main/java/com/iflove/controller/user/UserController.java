package com.iflove.controller.user;

import com.iflove.entity.RestBean;
import com.iflove.entity.ResultCodeEnum;
import com.iflove.entity.dto.Account;
import com.iflove.entity.vo.response.UserInfoVO;
import com.iflove.service.AccountService;
import com.iflove.utils.MessageHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 根据 id 获取用户基本信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("info")
    public RestBean<UserInfoVO> getUserInfo(@RequestParam("id") String id) {
        return MessageHandler.messageHandle(Long.valueOf(id), accountService::getUserInfoById);
    }

    /**
     * 上传/修改用户头像
     * @param file 头像文件
     * @return 用户信息
     */
    @PutMapping("avatar/upload")
    public RestBean<UserInfoVO> uploadAvatar(MultipartFile file) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = accountService.query().select("id").eq("username", username).eq("is_deleted", 0).one().getId();
        return MessageHandler.messageHandle(() ->
                accountService.saveUserAvatar(file, id));
    }
}
