package com.easyshare.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowVo {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private LocalDateTime lastUpdate;
}
