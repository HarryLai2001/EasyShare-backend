package com.easyshare.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDataVo {
    private Long userId;
    private Integer fansCnt;
    private Integer followsCnt;
    private Boolean isFollowed;
}
