package com.easyshare.service.impl;

import com.easyshare.dao.CommentDao;
import com.easyshare.dao.PostDao;
import com.easyshare.pojo.common.ActionCode;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.po.Comment;
import com.easyshare.pojo.po.SystemMessage;
import com.easyshare.pojo.vo.CommentVo;
import com.easyshare.pojo.vo.PostVo;
import com.easyshare.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.CommentDto;
import com.easyshare.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    @Transactional
    public Result createComment(Long userId, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPostId(commentDto.getPostId());
        comment.setFromUserId(userId);
        comment.setContent(commentDto.getContent());
        comment.setCommentAt(LocalDateTime.now());
        comment.setParentId(commentDto.getParentId());
        comment.setToUserId(commentDto.getToUserId());
        commentDao.addComment(comment); // 添加评论
        postDao.incrementCommentsCnt(comment.getPostId()); // 更新帖子评论数+1
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setActionId(comment.getId());
        systemMessage.setActionUserId(userId);
        systemMessage.setActionTime(LocalDateTime.now());
        systemMessage.setActionCode(ActionCode.Comment.getCode());
        systemMessage.setIsRead(false);
        if (commentDto.getToUserId() != null) {
            systemMessage.setUserId(commentDto.getToUserId());
        }
        if (commentDto.getToUserId() == null && commentDto.getParentId() != null) {
            CommentVo parentComment = commentDao.findOneComment(commentDto.getParentId());
            systemMessage.setUserId(parentComment.getFromUserId());
        }
        if (commentDto.getToUserId() == null && commentDto.getParentId() == null) {
            PostVo post = postDao.findOnePost(commentDto.getPostId());
            systemMessage.setUserId(post.getAuthorId());
        }
        if (!userId.equals(systemMessage.getUserId())) { // 消息接收方非本人才发送
            systemMessageService.sendActionMessage(systemMessage);
        }
        return new Result(ResultCode.SUCCESS.getCode(), "创建评论成功", null);
    }

    @Override
    public Result getHieraCommentsOfOnePost(Long postId) {
        List<CommentVo> comments = commentDao.findCommentsOfOnePost(postId);
        return new Result(ResultCode.SUCCESS.getCode(), "获取所有评论成功", comments);
    }

    @Override
    @Transactional
    public Result deleteComment(Long userId, Long commentId) {
        CommentVo commentVo = commentDao.findOneComment(commentId);
        if (commentVo == null) {
            return new Result<>(ResultCode.ERROR.getCode(), "评论不存在，不能删除", null);
        }
        if (!commentVo.getFromUserId().equals(userId)) {
            return new Result<>(ResultCode.ERROR.getCode(), "非本人发表的评论不能删除", null);
        }
        commentDao.deleteComment(commentId); // 删除评论
        postDao.decrementCommentsCnt(commentVo.getPostId()); // 更新帖子评论数-1
        return new Result(ResultCode.SUCCESS.getCode(), "删除评论成功", null);
    }
}
