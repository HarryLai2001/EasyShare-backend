package com.easyshare.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVo {
    private Long id;
    private String content;
    private Long authorId;
    private String author;
    private String avatar;
    private LocalDateTime publishAt;
    private Integer viewsCnt;
    private Boolean isLiked;  // 是否被当前用户点赞
    private Integer likesCnt;
    private Integer commentsCnt;
    private LocalDateTime lastUpdate;
}
