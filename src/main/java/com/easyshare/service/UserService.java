package com.easyshare.service;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.LoginDto;
import com.easyshare.pojo.dto.ResetPasswordDto;
import com.easyshare.pojo.dto.SignupDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public Result signup(SignupDto signupDto);
    public Result login(LoginDto loginDto);
    public Result updateAvatar(Long id, MultipartFile avatarImg);
    public Result getUserInfo(Long id);
    public Result getUserData(Long myId, Long id);
    public Result logout(Long id);
    public Result resetPassword(Long id, ResetPasswordDto resetPasswordDto);
    public Result deleteAccount(Long id, String password);
}
