package com.easyshare.service;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.po.SystemMessage;

import java.util.List;

public interface SystemMessageService {
    public void sendActionMessage(SystemMessage systemMessage);
    public Result getLikeMessageRecord(Long userId, Long lastId, Integer pageSize);
    public Result setAllLikeMessageRead(Long userId);
    public Result getUnreadLikeMessageCount(Long userId);
    public Result getFollowMessageRecord(Long userId, Long lastId, Integer pageSize);
    public Result setAllFollowMessageRead(Long userId);
    public Result getUnreadFollowMessageCount(Long userId);
    public Result getCommentMessageRecord(Long userId, Long lastId, Integer pageSize);
    public Result setAllCommentMessageRead(Long userId);
    public Result getUnreadCommentMessageCount(Long userId);
}
