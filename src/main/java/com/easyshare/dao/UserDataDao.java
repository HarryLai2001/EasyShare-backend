package com.easyshare.dao;

import com.easyshare.pojo.vo.UserDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface UserDataDao {
    public void addUserData(@Param("userId") Long userId,
                            @Param("lastUpdate") LocalDateTime lastUpdate);
    public UserDataVo findUserData(@Param("userId") Long userId);

    public void incrementFansCnt(@Param("userId") Long userId,
                                 @Param("lastUpdate") LocalDateTime lastUpdate);

    public void decrementFansCnt(@Param("userId") Long userId,
                                 @Param("lastUpdate") LocalDateTime lastUpdate);

    public void incrementFollowsCnt(@Param("userId") Long userId,
                                 @Param("lastUpdate") LocalDateTime lastUpdate);

    public void decrementFollowsCnt(@Param("userId") Long userId,
                                    @Param("lastUpdate") LocalDateTime lastUpdate);
}
