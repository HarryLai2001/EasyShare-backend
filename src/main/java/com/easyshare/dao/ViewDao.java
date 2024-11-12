package com.easyshare.dao;

import com.easyshare.pojo.po.View;
import com.easyshare.pojo.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ViewDao {
    public void addView(View view);

    public View findLatestView(@Param("postId") Long postId,
                               @Param("userId") Long userId);

    public List<PostVo> findRecentViewLog(@Param("userId") Long userId,
                                          @Param("dayLimit") Integer dayLimit,
                                          @Param("pageSize") Integer pageSize);
}
