package com.easyshare.controller;

import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.LoginDto;
import com.easyshare.pojo.dto.ResetPasswordDto;
import com.easyshare.pojo.dto.SignupDto;
import com.easyshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Result signup(@Validated @RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }


    @GetMapping("/user-info")
    public Result getUserInfo(@RequestJwtClaim("id") Long id,
                              @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return userService.getUserInfo(id);
        } else {
            return userService.getUserInfo(userId);
        }
    }

    @GetMapping("/user-data")
    public Result getUserData(@RequestJwtClaim("id") Long myUserId,
                              @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return userService.getUserData(myUserId, myUserId);
        } else {
            return userService.getUserData(myUserId, userId);
        }
    }

    @PutMapping("/logout")
    public Result logout(@RequestJwtClaim("id") Long id) {
        return userService.logout(id);
    }

    @PutMapping("/update-avatar")
    public Result updateAvatar(@RequestJwtClaim("id") Long id,
                               @RequestPart("avatar") MultipartFile avatarImg) {
        return userService.updateAvatar(id, avatarImg);
    }

    @PutMapping("/reset-password")
    public Result resetPassword(@RequestJwtClaim("id") Long id,
                                @RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.resetPassword(id, resetPasswordDto);
    }

    @DeleteMapping("/delete")
    public Result deleteAccount(@RequestJwtClaim("id") Long id,
                                @RequestParam("password") String password) {
        System.out.println(id);
        System.out.println(password);
        return userService.deleteAccount(id, password);
    }
}
