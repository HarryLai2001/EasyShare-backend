package com.easyshare.controller;

import com.easyshare.annotation.RequestBodyParam;
import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.ChatMessageDto;
import com.easyshare.service.ChatMessageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping("/send")
    public Result sendMessage(@RequestJwtClaim("id") Long userId,
                              @Validated @RequestBody ChatMessageDto chatMessageDto) {
        return chatMessageService.sendMessage(userId, chatMessageDto);
    }

    @GetMapping("/chat-history")
    public Result getChatHistory(@RequestJwtClaim("id") Long myUserId,
                                 @RequestParam("userId") Long userId,
                                 @RequestParam(value = "lastId", required = false) Long lastId,
                                 @RequestParam("pageSize") Integer pageSize) {
        return chatMessageService.getChatRecord(myUserId, userId, lastId, pageSize);
    }

    @GetMapping("/chat-list")
    public Result getChatList(@RequestJwtClaim("id") Long userId) {
        return chatMessageService.getChatList(userId);
    }

    @PutMapping("/read")
    public Result setMessageRead(@RequestJwtClaim("id") Long myUserId,
                                 @NotNull @RequestBodyParam("userId") Long userId) {
        return chatMessageService.setMessageRead(userId, myUserId);
    }
}
