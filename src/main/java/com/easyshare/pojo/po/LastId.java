package com.easyshare.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("lastId")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LastId {
    @Id
    private String id;
    private String collectionName;  // 对应的集合名称
    private Long lastId; // 最后一个id的值
}
