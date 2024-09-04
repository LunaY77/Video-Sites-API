package com.iflove.controller.user;

import com.iflove.entity.RestBean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("api/validatation")
public class ValidationTestController {

    // 为什么不行？？？？？？？？？？？？？？？？？
    @PreAuthorize("hasRole('admin')")
    @GetMapping("test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            String username = (String) authentication.getPrincipal();
            return "用户名:" + username;
        } else {
            return "认证信息获取失败";
        }
    }
}
