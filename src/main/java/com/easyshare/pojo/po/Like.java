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
public class Like {
    private Long id;
    private Long postId;
    private Long userId;
    private LocalDateTime lastUpdate;
    private Boolean isCancelled;
}
