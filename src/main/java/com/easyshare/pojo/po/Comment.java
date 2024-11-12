package com.easyshare.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private Long id;
    private Long postId;
    private Long fromUserId;
    private String content;
    private LocalDateTime commentAt;
    private Long parentId;
    private Long toUserId;
    private Boolean isDeleted;
}
