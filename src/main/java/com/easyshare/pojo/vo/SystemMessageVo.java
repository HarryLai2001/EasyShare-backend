package com.easyshare.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemMessageVo {
    private Long id;
    private Long actionId;
    private Long actionUserId;
    private String actionUsername;
    private String actionUserAvatar;
    private Object actionContent;
    private Integer actionCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actionTime;
}
