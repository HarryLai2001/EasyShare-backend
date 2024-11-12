package com.easyshare.service;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.ChatMessageDto;

public interface ChatMessageService {
    public Result sendMessage(Long userId, ChatMessageDto chatMessageDto);
    public Result getChatRecord(Long userId1, Long userId2, Long lastId, Integer pageSize);
    public Result getChatList(Long userId);
    public Result setMessageRead(Long fromUserId, Long toUserId);
}
