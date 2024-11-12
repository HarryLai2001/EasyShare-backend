package com.easyshare.dao;

import com.easyshare.pojo.po.Follow;
import com.easyshare.pojo.vo.FollowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FollowDao {
    public void addFollow(Follow follow);

    public Follow findFollow(@Param("followId") Long followId,
                             @Param("followerId") Long followerId,
                             @Param("followeeId") Long followeeId);

    public List<FollowVo> findAllFollowers(@Param("userId") Long userId,
                                           @Param("lastId") Long lastId,
                                           @Param("pageSize") Integer pageSize);

    public List<FollowVo> findAllFollowees(@Param("userId") Long userId,
                                           @Param("lastId") Long lastId,
                                           @Param("pageSize") Integer pageSize);

    public void updateFollow(@Param("followerId") Long followerId,
                             @Param("followeeId") Long followeeId,
                             @Param("isCancelled") Boolean isCancelled,
                             @Param("lastUpdate") LocalDateTime lastUpdate);
}
