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
public class Follow {
    private Long id;
    private Long followerId;
    private Long followeeId;
    private LocalDateTime lastUpdate;
    private Boolean isCancelled;
}
