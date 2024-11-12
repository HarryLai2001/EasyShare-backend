package com.easyshare.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.easyshare.dao.CommentDao;
import com.easyshare.dao.FollowDao;
import com.easyshare.dao.LikeDao;
import com.easyshare.dao.UserDao;
import com.easyshare.pojo.common.ActionCode;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.po.Follow;
import com.easyshare.pojo.po.Like;
import com.easyshare.pojo.po.SystemMessage;
import com.easyshare.pojo.vo.SystemMessageVo;
import com.easyshare.pojo.vo.UserInfoVo;
import com.easyshare.service.SystemMessageService;
import com.easyshare.socketio.SocketIOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMessageServiceImpl implements SystemMessageService {
    @Autowired
    private SocketIOManager socketIOManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private FollowDao followDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public void sendActionMessage(SystemMessage systemMessage) {
        mongoTemplate.insert(systemMessage);
        SocketIOClient client = socketIOManager.getClient(systemMessage.getUserId());
        if (client != null) {
            client.sendEvent("action", systemMessage.getActionCode());
        } else {
            redisTemplate.opsForList().leftPush("unsentMsg::" + systemMessage.getUserId(), systemMessage.getActionCode());
        }
    }

    @Override
    public Result getLikeMessageRecord(Long userId, Long lastId, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Like.getCode())
        ));
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        if (lastId != null) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }
        query.limit(pageSize);
        query.fields().include("_id", "actionId", "actionUserId", "actionCode", "actionTime");
        List<SystemMessageVo> messageList = mongoTemplate.find(query, SystemMessageVo.class, "systemMessage");
        UserInfoVo userInfo = new UserInfoVo();
        for (SystemMessageVo message : messageList) {
            userInfo = userDao.getUserInfo(message.getActionUserId());
            message.setActionUsername(userInfo.getUsername());
            message.setActionUserAvatar(userInfo.getAvatar());
            message.setActionContent(likeDao.findLike(message.getActionId(), null, null));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取点赞消息列表成功", messageList);
    }

    @Override
    public Result setAllLikeMessageRead(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Like.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, SystemMessage.class);
        return new Result(ResultCode.SUCCESS.getCode(), "点赞消息全部修改为已读状态", null);
    }

    @Override
    public Result getUnreadLikeMessageCount(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Like.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Long unreadLikeMessageCount = mongoTemplate.count(query, "systemMessage");
        return new Result(ResultCode.SUCCESS.getCode(), "获取未读点赞消息数成功", unreadLikeMessageCount);
    }


    @Override
    public Result getFollowMessageRecord(Long userId, Long lastId, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Follow.getCode())
        ));
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        if (lastId != null) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }
        query.limit(pageSize);
        query.fields().include("_id", "actionId", "actionUserId", "actionCode", "actionTime");
        List<SystemMessageVo> messageList = mongoTemplate.find(query, SystemMessageVo.class, "systemMessage");
        UserInfoVo userInfo = new UserInfoVo();
        for (SystemMessageVo message : messageList) {
            userInfo = userDao.getUserInfo(message.getActionUserId());
            message.setActionUsername(userInfo.getUsername());
            message.setActionUserAvatar(userInfo.getAvatar());
            message.setActionContent(followDao.findFollow(message.getActionId(), null, null));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取关注消息列表成功", messageList);
    }

    @Override
    public Result setAllFollowMessageRead(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Follow.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, SystemMessage.class);
        return new Result(ResultCode.SUCCESS.getCode(), "关注消息全部修改为已读状态", null);
    }

    @Override
    public Result getUnreadFollowMessageCount(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Follow.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Long unreadFollowMessageCount = mongoTemplate.count(query, "systemMessage");
        return new Result(ResultCode.SUCCESS.getCode(), "获取未读关注消息数成功", unreadFollowMessageCount);
    }

    @Override
    public Result getCommentMessageRecord(Long userId, Long lastId, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Comment.getCode())
        ));
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        if (lastId != null) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }
        query.limit(pageSize);
        query.fields().include("_id", "actionId", "actionUserId", "actionCode", "actionTime");
        List<SystemMessageVo> messageList = mongoTemplate.find(query, SystemMessageVo.class, "systemMessage");
        UserInfoVo userInfo = new UserInfoVo();
        for (SystemMessageVo message : messageList) {
            userInfo = userDao.getUserInfo(message.getActionUserId());
            message.setActionUsername(userInfo.getUsername());
            message.setActionUserAvatar(userInfo.getAvatar());
            message.setActionContent(commentDao.findOneComment(message.getActionId()));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取评论消息列表成功", messageList);
    }

    @Override
    public Result setAllCommentMessageRead(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Comment.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, SystemMessage.class);
        return new Result(ResultCode.SUCCESS.getCode(), "评论消息全部修改为已读状态", null);
    }

    @Override
    public Result getUnreadCommentMessageCount(Long userId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("actionCode").is(ActionCode.Comment.getCode()),
                Criteria.where("isRead").is(false)
        ));
        Long unreadCommentMessageCount = mongoTemplate.count(query, "systemMessage");
        return new Result(ResultCode.SUCCESS.getCode(), "获取未读评论消息数成功", unreadCommentMessageCount);
    }
}
