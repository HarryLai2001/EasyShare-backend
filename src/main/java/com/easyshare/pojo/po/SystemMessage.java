package com.easyshare.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("systemMessage")
public class SystemMessage {
    @Id
    private Long id;
    @Indexed
    private Long userId;
    private Long actionId;
    private Long actionUserId;
    private Integer actionCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actionTime;
    private Boolean isRead;
}
