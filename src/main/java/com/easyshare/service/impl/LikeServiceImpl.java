package com.easyshare.service.impl;

import com.easyshare.dao.LikeDao;
import com.easyshare.dao.PostDao;
import com.easyshare.pojo.common.ActionCode;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.po.Like;
import com.easyshare.pojo.po.SystemMessage;
import com.easyshare.pojo.vo.PostVo;
import com.easyshare.service.LikeService;
import com.easyshare.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    public Boolean getLikeStatus(Long postId, Long userId) {
        Like like = likeDao.findLike(null, postId, userId);
        Boolean isLiked = like != null && !like.getIsCancelled() ? true : false;
        return isLiked;
    }

    @Override
    @Transactional
    public Result createLike(Long userId, Long postId) {
        Like oldLike = likeDao.findLike(null, postId, userId);
        if (oldLike != null && !oldLike.getIsCancelled()) {
            return new Result(ResultCode.ERROR.getCode(), "不能重复点赞", null);
        }
        if (oldLike != null && oldLike.getIsCancelled()) {
            likeDao.updateLike(postId, userId, false, LocalDateTime.now());
        } else {
            Like like = new Like();
            like.setPostId(postId);
            like.setUserId(userId);
            like.setLastUpdate(LocalDateTime.now());
            likeDao.addLike(like);  // 添加点赞记录
            PostVo post = postDao.findOnePost(postId);
            SystemMessage systemMessage = new SystemMessage();
            systemMessage.setUserId(post.getAuthorId());
            systemMessage.setActionId(like.getId());
            systemMessage.setActionUserId(userId);
            systemMessage.setActionCode(ActionCode.Like.getCode());
            systemMessage.setActionTime(LocalDateTime.now());
            systemMessage.setIsRead(false);
            systemMessageService.sendActionMessage(systemMessage);
        }
        postDao.incrementLikesCnt(postId);  // 对应帖子点赞数+1
        return new Result(ResultCode.SUCCESS.getCode(), "点赞成功", null);
    }

    @Override
    @Transactional
    public Result cancelLike(Long userId, Long postId) {
        if (!getLikeStatus(postId, userId)) {
            return new Result(ResultCode.ERROR.getCode(), "未点赞，不能取消点赞", null);
        }
        likeDao.updateLike(postId, userId, true, LocalDateTime.now());  // 取消点赞记录
        postDao.decrementLikesCnt(postId);  // 对应帖子点赞数-1
        PostVo post = postDao.findOnePost(postId);
        return new Result(ResultCode.SUCCESS.getCode(), "取消点赞成功", null);
    }
}
