package com.easyshare.dao;

import com.easyshare.pojo.po.User;
import com.easyshare.pojo.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface UserDao {
    public void addUser(User user);

    public User findUser(@Param("id") Long id,
                         @Param("username") String username);

    public UserInfoVo getUserInfo(@Param("id") Long id);

    public void updateUser(@Param("id") Long id,
                           @Param("email") String email,
                           @Param("password") String password,
                           @Param("avatar") String avatar,
                           @Param("lastUpdate") LocalDateTime lastUpdate);

    public void deleteUser(@Param("id") Long id,
                           @Param("lastUpdate") LocalDateTime lastUpdate);
}
