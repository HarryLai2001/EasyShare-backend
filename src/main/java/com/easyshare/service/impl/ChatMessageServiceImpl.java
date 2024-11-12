package com.easyshare.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.easyshare.dao.UserDao;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.pojo.dto.ChatMessageDto;
import com.easyshare.pojo.po.ChatMessage;
import com.easyshare.pojo.vo.ChatInfoVo;
import com.easyshare.pojo.vo.UserInfoVo;
import com.easyshare.service.ChatMessageService;
import com.easyshare.socketio.SocketIOManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SocketIOManager socketIOManager;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Result sendMessage(Long userId, ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromUserId(userId);
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setToUserId(chatMessageDto.getToUserId());
        chatMessage.setSendAt(LocalDateTime.now());
        chatMessage.setIsRead(false);
        mongoTemplate.insert(chatMessage);
        SocketIOClient client = socketIOManager.getClient(chatMessage.getToUserId());
        if (client != null) {
            client.sendEvent("chat", chatMessage);
        }
        return new Result(ResultCode.SUCCESS.getCode(), "消息发送成功", chatMessage);
    }

    @Override
    public Result getChatRecord(Long userId1, Long userId2, Long lastId, Integer pageSize) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        new Criteria().andOperator(
                                Criteria.where("fromUserId").is(userId1),
                                Criteria.where("toUserId").is(userId2)
                        ),
                        new Criteria().andOperator(
                                Criteria.where("fromUserId").is(userId2),
                                Criteria.where("toUserId").is(userId1)
                        )
                ));
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        if (lastId != null) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }
        query.limit(pageSize);
        query.fields().include("_id", "fromUserId", "content", "sendAt");
        List<ChatMessage> chatRecords = mongoTemplate.find(query, ChatMessage.class, "chatMessage");
        return new Result<>(ResultCode.SUCCESS.getCode(), "获取历史消息成功", chatRecords);
    }

    @Override
    public Result getChatList(Long userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("fromUserId").is(userId)),
                Aggregation.project("_id", "toUserId", "content", "sendAt", "isRead")
                        .and("toUserId").as("userId"),
                new UnionWithOperation(
                        "chatMessage",
                        AggregationPipeline.of(
                                Aggregation.match(Criteria.where("toUserId").is(userId)),
                                Aggregation.project("_id", "toUserId", "content", "sendAt", "isRead")
                                        .and("fromUserId").as("userId")
                        ),
                        null
                ),
                Aggregation.sort(Sort.Direction.DESC, "_id"),
                Aggregation.group("userId")
                        .first("userId").as("userId")
                        .first("_id").as("messageId")
                        .first("content").as("content")
                        .first("sendAt").as("sendAt")
                        .sum(ConditionalOperators.when(new Criteria().andOperator(
                                Criteria.where("toUserId").is(userId),
                                Criteria.where("isRead").is(false)
                        )).then(1).otherwise(0)).as("unreadCount"),
                Aggregation.sort(Sort.Direction.DESC, "messageId"),
                Aggregation.project("userId", "content", "sendAt", "unreadCount")
        );
        List<ChatInfoVo> chatList = mongoTemplate.aggregate(aggregation, "chatMessage", ChatInfoVo.class).getMappedResults();
        UserInfoVo userInfoVo = new UserInfoVo();
        for (ChatInfoVo chat: chatList) {
            userInfoVo =  userDao.getUserInfo(chat.getUserId());
            if (userInfoVo != null) {
                chat.setUsername(userInfoVo.getUsername());
                chat.setAvatar(userInfoVo.getAvatar());
            }
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取聊天列表成功", chatList);
    }

    @Override
    public Result setMessageRead(Long fromUserId, Long toUserId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("fromUserId").is(fromUserId),
                Criteria.where("toUserId").is(toUserId),
                Criteria.where("isRead").is(false)
        ));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, ChatMessage.class);
        return new Result(ResultCode.SUCCESS.getCode(), "聊天记录已设置为已读", null);
    }
}
