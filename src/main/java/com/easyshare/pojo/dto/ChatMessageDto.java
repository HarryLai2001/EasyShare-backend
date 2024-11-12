package com.easyshare.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDto {
    @NotBlank(message = "消息内容不能为空")
    private String content;
    @NotNull(message = "接收消息用户不能为空")
    private Long toUserId;
}
