package com.easyshare.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.easyshare.dao.FollowDao;
import com.easyshare.dao.UserDataDao;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.dao.UserDao;
import com.easyshare.pojo.dto.LoginDto;
import com.easyshare.pojo.dto.ResetPasswordDto;
import com.easyshare.pojo.dto.SignupDto;
import com.easyshare.pojo.po.Follow;
import com.easyshare.pojo.po.User;
import com.easyshare.pojo.vo.UserDataVo;
import com.easyshare.pojo.vo.UserInfoVo;
import com.easyshare.service.FollowService;
import com.easyshare.service.UserService;
import com.easyshare.socketio.SocketIOManager;
import com.easyshare.utils.AliyunOssUtils;
import com.easyshare.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDataDao userDataDao;
    @Autowired
    private FollowService followService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AliyunOssUtils aliyunOssUtils;

    @Override
    @Transactional
    public Result signup(SignupDto signupDto) {
        User user = userDao.findUser(null, signupDto.getUsername());
        if (user != null) {
            return new Result(ResultCode.ERROR.getCode(), "用户名已存在", null);
        }
        User newUser = new User();
        newUser.setUsername(signupDto.getUsername());
        newUser.setEmail(signupDto.getEmail());
        newUser.setPassword(DigestUtils.md5DigestAsHex(signupDto.getPassword().getBytes())); // md5加密
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastUpdate(LocalDateTime.now());
        userDao.addUser(newUser);
        userDataDao.addUserData(newUser.getId(), LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "注册成功", null);
    }

    @Override
    @Transactional
    public Result login(LoginDto loginDto) {
        User user = userDao.findUser(null, loginDto.getUsername());
        if (user == null || user.getIsDeleted() || !DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()).equals(user.getPassword())) {
            return new Result(ResultCode.ERROR.getCode(), "用户名或密码错误", null);
        }
        /* 生成JWT token*/
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = jwtUtils.genToken(claims);
        /* 将生成的JWT token存入redis中，设置有效时间为12小时 */
        redisTemplate.opsForValue().set("token::" + user.getId(), token, 12, TimeUnit.HOURS);
        return new Result(ResultCode.SUCCESS.getCode(), "登陆成功", token);
    }

    @Override
    public Result getUserInfo(Long id) {
        UserInfoVo userInfoVo = userDao.getUserInfo(id);
        if (userInfoVo == null) {
            return new Result(ResultCode.ERROR.getCode(), "此用户不存在", null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取用户信息成功", userInfoVo);
    }

    @Override
    public Result getUserData(Long myId, Long id) {
        UserDataVo userData = userDataDao.findUserData(id);
        if (userData == null) {
            return new Result(ResultCode.ERROR.getCode(), "非找到用户数据", null);
        }
        if (myId != id) {
            userData.setIsFollowed(followService.getFollowStatus(myId, id));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取用户数据成功", userData);
    }

    @Override
    public Result logout(Long id) {
        redisTemplate.delete("token::" + id);
        return new Result<>(ResultCode.SUCCESS.getCode(), "退出登陆成功", null);
    }

    @Override
    @Transactional
    public Result updateAvatar(Long id, MultipartFile avatarImg) {
        try {
            byte[] byteArr = avatarImg.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            String url = aliyunOssUtils.upload("avatar/" + id + ".png", inputStream);
            userDao.updateUser(id, null, null, url, LocalDateTime.now());
            return new Result(ResultCode.SUCCESS.getCode(), "头像修改成功", null);
        } catch (Exception ex) {
            return new Result(ResultCode.ERROR.getCode(), "头像上传失败，请重试", null);
        }

    }

    @Override
    @Transactional
    public Result resetPassword(Long id, ResetPasswordDto resetPasswordDto) {
        User user = userDao.findUser(id, null);
        if (user == null) {
            return new Result(ResultCode.ERROR.getCode(), "用户不存在", null);
        }
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(resetPasswordDto.getOldPassword().getBytes()))) {
            return new Result(ResultCode.ERROR.getCode(), "原始密码错误", null);
        }
        userDao.updateUser(user.getId(),
                null,
                DigestUtils.md5DigestAsHex(resetPasswordDto.getNewPassword().getBytes()),
                null,
                LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "重置密码成功", null);
    }

    @Override
    @Transactional
    public Result deleteAccount(Long id, String password) {
        User user = userDao.findUser(id, null);
        System.out.println(user);
        if (user == null || !user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return new Result(ResultCode.ERROR.getCode(), "密码不正确，无法注销账号", null);
        }
        userDao.deleteUser(id, LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "注销账号成功", null);
    }
}
