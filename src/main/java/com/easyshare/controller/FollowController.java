package com.easyshare.controller;

import com.easyshare.annotation.RequestBodyParam;
import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.service.FollowService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/create")
    public Result createFollow(@RequestJwtClaim("id") Long followerId,
                               @NotNull @RequestBodyParam("followeeId") Long followeeId) {
        return followService.createFollow(followerId, followeeId);
    }

    @PostMapping("/cancel")
    public Result cancelFollow(@RequestJwtClaim("id") Long followerId,
                               @NotNull @RequestBodyParam("followeeId") Long followeeId) {

        return followService.cancelFollow(followerId, followeeId);
    }

    @GetMapping("/fans-list")
    public Result findAllFans(@RequestJwtClaim("id") Long userId,
                              @RequestParam(value = "lastId", required = false) Long lastId,
                              @RequestParam("pageSize") Integer pageSize) {
        return followService.findAllFans(userId, lastId, pageSize);
    }

    @GetMapping("/follows-list")
    public Result findAllFollows(@RequestJwtClaim("id") Long userId,
                                 @RequestParam(value = "lastId", required = false) Long lastId,
                                 @RequestParam("pageSize") Integer pageSize) {
        return followService.findAllFollows(userId, lastId, pageSize);
    }
}
