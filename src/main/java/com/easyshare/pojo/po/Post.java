package com.easyshare.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post implements Serializable {
    private Long id;
    private String content;
    private Long authorId;
    private LocalDateTime publishAt;
    private Integer viewsCnt;
    private Integer likesCnt;
    private Integer commentsCnt;
    private Boolean isDeleted;
    private LocalDateTime lastUpdate;
}
