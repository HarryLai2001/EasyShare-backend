package com.easyshare.dao;

import com.easyshare.pojo.po.Post;
import com.easyshare.pojo.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PostDao {
    public void addPost(Post post);

    public PostVo findOnePost(@Param("postId") Long postId);

    public List<PostVo> findPosts(@Param("authorId") Long authorId,
                                  @Param("keywords") String keywords,
                                  @Param("lastId") Long lastId,
                                  @Param("pageSize") Integer pageSize);

    public List<PostVo> getHotPostsAtRandom(@Param("dateLimit") Integer dateLimit,
                                            @Param("pageSize") Integer pageSize);

    public void updatePostContent(@Param("postId") Long postId,
                                  @Param("content") String content,
                                  @Param("lastUpdate") LocalDateTime lastUpdate);

    public void incrementCommentsCnt(@Param("postId") Long postId);

    public void decrementCommentsCnt(@Param("postId") Long postId);

    public void incrementLikesCnt(@Param("postId") Long postId);

    public void decrementLikesCnt(@Param("postId") Long postId);

    public void incrementViewsCnt(@Param("postId") Long postId);

    public void deletePost(@Param("postId") Long postId,
                           @Param("lastUpdate") LocalDateTime lastUpdate);
}
