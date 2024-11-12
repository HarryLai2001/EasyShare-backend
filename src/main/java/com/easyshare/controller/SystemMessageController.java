package com.easyshare.controller;

import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system-message")
public class SystemMessageController {
    @Autowired
    private SystemMessageService systemMessageService;

    @GetMapping("/like-list")
    public Result getLikeMessageRecord(@RequestJwtClaim("id") Long userId,
                                       @RequestParam(value = "lastId", required = false) Long lastId,
                                       @RequestParam("pageSize") Integer pageSize) {
        return systemMessageService.getLikeMessageRecord(userId, lastId, pageSize);
    }

    @PutMapping("/like-read")
    public Result setAllLikeMessageRead(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.setAllLikeMessageRead(userId);
    }

    @GetMapping("/like-unread-count")
    public Result getUnreadLikeMessageCount(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.getUnreadLikeMessageCount(userId);
    }

    @GetMapping("/follow-list")
    public Result getFollowMessageRecord(@RequestJwtClaim("id") Long userId,
                                         @RequestParam(value = "lastId", required = false) Long lastId,
                                         @RequestParam("pageSize") Integer pageSize) {
        return systemMessageService.getFollowMessageRecord(userId, lastId, pageSize);
    }

    @PutMapping("/follow-read")
    public Result setAllFollowMessageRead(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.setAllFollowMessageRead(userId);
    }

    @GetMapping("/follow-unread-count")
    public Result getUnreadFollowMessageCount(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.getUnreadFollowMessageCount(userId);
    }

    @GetMapping("/comment-list")
    public Result getCommentMessageRecord(@RequestJwtClaim("id") Long userId,
                                          @RequestParam(value = "lastId", required = false) Long lastId,
                                          @RequestParam("pageSize") Integer pageSize) {
        return systemMessageService.getCommentMessageRecord(userId, lastId, pageSize);
    }

    @PutMapping("/comment-read")
    public Result setAllCommentMessageRead(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.setAllCommentMessageRead(userId);
    }

    @GetMapping("/comment-unread-count")
    public Result getUnreadCommentMessageCount(@RequestJwtClaim("id") Long userId) {
        return systemMessageService.getUnreadCommentMessageCount(userId);
    }
}
