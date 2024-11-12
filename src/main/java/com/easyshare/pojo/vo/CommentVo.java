package com.easyshare.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentVo {
    private Long id;
    private Long postId;
    private Long fromUserId;
    private String fromUsername;
    private String fromUserAvatar;
    private String content;
    private LocalDateTime commentAt;
    private Long parentId;
    private Long toUserId;
    private String toUsername;
    private String toUserAvatar;
    private List<CommentVo> childComments;
    private Boolean isDeleted;
}
