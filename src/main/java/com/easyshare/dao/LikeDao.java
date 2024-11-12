package com.easyshare.dao;

import com.easyshare.pojo.po.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface LikeDao {
    public void addLike(Like like);

    public Like findLike(@Param("likeId") Long likeId,
                         @Param("postId") Long postId,
                         @Param("userId") Long userId);

    public void updateLike(@Param("postId") Long postId,
                           @Param("userId") Long userId,
                           @Param("isCancelled") Boolean isCancelled,
                           @Param("lastUpdate") LocalDateTime lastUpdate);
}
