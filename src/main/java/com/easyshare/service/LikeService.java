package com.easyshare.service;

import com.easyshare.pojo.common.Result;

public interface LikeService {
    public Boolean getLikeStatus(Long postId, Long userId);
    public Result createLike(Long userId, Long postId);
    public Result cancelLike(Long userId, Long postId);
}
