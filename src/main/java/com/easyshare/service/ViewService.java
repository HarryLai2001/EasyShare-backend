package com.easyshare.service;

import com.easyshare.pojo.common.Result;

public interface ViewService {
    public Result createViewLog(Long userId, Long postId);
    public Result getRecentViewLog(Long userId, Integer dayLimit, Integer pageSize);
}
