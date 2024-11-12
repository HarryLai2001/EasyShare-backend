package com.easyshare.service.impl;

import com.easyshare.dao.PostDao;
import com.easyshare.dao.ViewDao;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.po.View;
import com.easyshare.pojo.vo.PostVo;
import com.easyshare.service.LikeService;
import com.easyshare.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewDao viewDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private LikeService likeService;

    @Override
    @Transactional
    public Result createViewLog(Long userId, Long postId) {
        View oldView = viewDao.findLatestView(postId, userId);
        if (oldView != null && oldView.getCreatedAt().plusMinutes(5L).isAfter(LocalDateTime.now())) {
            return new Result(ResultCode.ERROR.getCode(), "浏览过于频繁", null);
        }
        View newView = new View();
        newView.setPostId(postId);
        newView.setUserId(userId);
        newView.setCreatedAt(LocalDateTime.now());
        viewDao.addView(newView);
        postDao.incrementViewsCnt(postId);  // 相应帖子浏览记录+1
        return new Result(ResultCode.SUCCESS.getCode(), "创建浏览记录成功", null);
    }

    @Override
    public Result getRecentViewLog(Long userId, Integer dayLimit, Integer pageSize) {
        List<PostVo> recentViewLog = viewDao.findRecentViewLog(userId, dayLimit, pageSize);
        for (PostVo post : recentViewLog) {
            post.setIsLiked(likeService.getLikeStatus(post.getId(), userId));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取最近浏览记录成功", recentViewLog);
    }
}
