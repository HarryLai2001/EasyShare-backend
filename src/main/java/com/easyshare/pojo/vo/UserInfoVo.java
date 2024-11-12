package com.easyshare.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private String avatar;
}
