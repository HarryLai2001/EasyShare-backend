package com.easyshare.service;

import com.easyshare.pojo.common.Result;

public interface FollowService {
    public Boolean getFollowStatus(Long followerId, Long followeeId);
    public Result createFollow(Long followerId, Long followeeId);
    public Result cancelFollow(Long followerId, Long followeeId);
    public Result findAllFans(Long userId, Long lastId, Integer pageSize);
    public Result findAllFollows(Long userId, Long lastId, Integer pageSize);
}
