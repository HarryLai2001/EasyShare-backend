package com.easyshare.service;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.UpdatePostDto;

public interface PostService {

    public Result createPost(Long userId, String content);

    public Result getAllPostsOfOneUser(Long authorId, Long userId, Long lastId, Integer pageSize);

    public Result getHotPostsAtRandom(Long userId, Integer dateLimit, Integer pageSize);

    public Result searchPosts(String keywords, Long userId, Long lastId, Integer pageSize);

    public Result getPostByPostId(Long postId, Long userId);

    public Result updatePost(Long userId, UpdatePostDto updatePostDto);

    public Result deletePost(Long userId, Long postId);
}
