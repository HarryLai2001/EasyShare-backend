package com.easyshare.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String Avatar;                   // 头像存储地址
    private Boolean isDeleted;               // 软删除
    private LocalDateTime lastUpdate;
}
