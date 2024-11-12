package com.easyshare.listener;

import com.easyshare.pojo.po.ChatMessage;
import com.easyshare.pojo.po.LastId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageIdEventListener extends AbstractMongoEventListener<ChatMessage> {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ChatMessage> event) {
        ChatMessage chatMessage = event.getSource();
        if (chatMessage.getId() == null) {
            chatMessage.setId(getNextId());
        }
    }

    private Long getNextId() {
        Query query = new Query(Criteria.where("collectionName").is("chatMessage"));
        Update update = new Update().inc("lastId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().upsert(true).returnNew(true);
        LastId lastId = mongoTemplate.findAndModify(query, update, options, LastId.class);
        return lastId.getLastId();

    }
}
