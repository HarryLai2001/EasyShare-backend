package com.easyshare.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
    @NotNull(message = "评论的帖子id不能为空")
    private Long postId;
    @NotBlank(message = "评论内容不能为空")
    private String content;
    private Long parentId;
    private Long toUserId;
}
