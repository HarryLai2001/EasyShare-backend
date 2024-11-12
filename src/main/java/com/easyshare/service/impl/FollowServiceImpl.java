package com.easyshare.service.impl;

import com.easyshare.dao.FollowDao;
import com.easyshare.dao.UserDataDao;
import com.easyshare.pojo.common.ActionCode;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.po.Follow;
import com.easyshare.pojo.po.SystemMessage;
import com.easyshare.pojo.vo.FollowVo;
import com.easyshare.service.FollowService;
import com.easyshare.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDao followDao;
    @Autowired
    private UserDataDao userDataDao;
    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    public Boolean getFollowStatus(Long followerId, Long followeeId) {
        Follow follow = followDao.findFollow(null, followerId, followeeId);
        Boolean isFollowed = follow != null && !follow.getIsCancelled()? true : false;
        return isFollowed;
    }

    @Override
    @Transactional
    public Result createFollow(Long followerId, Long followeeId) {
        Follow oldFollow = followDao.findFollow(null, followerId, followeeId);
        if (oldFollow != null && !oldFollow.getIsCancelled()) {
            return new Result(ResultCode.ERROR.getCode(), "不能重复关注用户", null);
        }
        if (oldFollow != null && oldFollow.getIsCancelled()) {
            followDao.updateFollow(followerId, followeeId, false, LocalDateTime.now());
        } else {
            Follow follow = new Follow();
            follow.setFollowerId(followerId);
            follow.setFolloweeId(followeeId);
            follow.setLastUpdate(LocalDateTime.now());
            followDao.addFollow(follow);
            SystemMessage systemMessage = new SystemMessage();
            systemMessage.setUserId(followeeId);
            systemMessage.setActionId(follow.getId());
            systemMessage.setActionUserId(followerId);
            systemMessage.setActionCode(ActionCode.Follow.getCode());
            systemMessage.setActionTime(LocalDateTime.now());
            systemMessage.setIsRead(false);
            systemMessageService.sendActionMessage(systemMessage);
        }
        userDataDao.incrementFollowsCnt(followerId, LocalDateTime.now());
        userDataDao.incrementFansCnt(followeeId, LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "关注成功", null);
    }

    @Override
    @Transactional
    public Result cancelFollow(Long followerId, Long followeeId) {
        if (!getFollowStatus(followerId, followeeId)) {
            return new Result<>(ResultCode.ERROR.getCode(), "未关注此用户，无法取消关注", null);
        }
        followDao.updateFollow(followerId, followeeId, true, LocalDateTime.now());
        userDataDao.decrementFollowsCnt(followerId, LocalDateTime.now());
        userDataDao.decrementFansCnt(followeeId, LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "取消关注成功", null);
    }

    @Override
    public Result findAllFans(Long userId, Long lastId, Integer pageSize) {
        List<FollowVo> followers = followDao.findAllFollowers(userId, lastId, pageSize);
        return new Result(ResultCode.SUCCESS.getCode(), "获取所有粉丝成功", followers);
    }

    @Override
    public Result findAllFollows(Long userId, Long lastId, Integer pageSize) {
        List<FollowVo> followees = followDao.findAllFollowees(userId, lastId, pageSize);
        return new Result(ResultCode.SUCCESS.getCode(), "获取所有关注用户成功", followees);
    }
}
