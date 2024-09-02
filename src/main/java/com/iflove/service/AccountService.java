package com.iflove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.entity.dto.Account;
import com.iflove.entity.vo.request.ConfirmResetVO;
import com.iflove.entity.vo.request.EmailRegisterVO;
import com.iflove.entity.vo.request.EmailResetVO;
import com.iflove.entity.vo.response.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 苍镜月
* @description 针对表【account】的数据库操作Service
* @createDate 2024-08-30 13:42:18
*/
public interface AccountService extends IService<Account> {
    Account getUserByName(String username);
    UserInfoVO getUserInfoById(Long id);
    String registerEmailVerifyCode(String type, String email, String ip);
    String registerEmailAccount(EmailRegisterVO vo);
    String resetConfirm(ConfirmResetVO vo);
    String resetEmailAccountPassword(EmailResetVO vo);
    boolean existsAccountByEmail(String email);
    UserInfoVO saveUserAvatar(MultipartFile file, Long id);
}
