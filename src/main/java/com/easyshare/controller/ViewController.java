package com.easyshare.controller;

import com.easyshare.annotation.RequestBodyParam;
import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.service.ViewService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/view")
public class ViewController {
    @Autowired
    private ViewService viewService;

    @PostMapping("/create")
    public Result createViewLog(@RequestJwtClaim("id") Long userId,
                                @NotNull @RequestBodyParam("postId") Long postId) {
        return viewService.createViewLog(userId, postId);
    }

    @GetMapping("/recent-view-log")
    public Result getRecentViewLog(@RequestJwtClaim("id") Long userId,
                                   @RequestParam("dayLimit") Integer dayLimit,
                                   @RequestParam("pageSize") Integer pageSize) {
        return viewService.getRecentViewLog(userId, dayLimit, pageSize);
    }
}
