package com.easyshare.controller;

import com.easyshare.annotation.RequestBodyParam;
import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.service.LikeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/create")
    public Result createLike(@RequestJwtClaim("id") Long userId,
                             @NotNull @RequestBodyParam("postId") Long postId) {
        return likeService.createLike(userId, postId);
    }

    @PostMapping("/cancel")
    public Result cancelLike(@RequestJwtClaim("id") Long userId,
                             @NotNull @RequestBodyParam("postId") Long postId) {
        return likeService.cancelLike(userId, postId);
    }
}
