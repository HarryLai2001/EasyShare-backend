package com.easyshare.service.impl;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.dao.PostDao;
import com.easyshare.pojo.dto.UpdatePostDto;
import com.easyshare.pojo.po.Post;
import com.easyshare.pojo.vo.PostVo;
import com.easyshare.service.LikeService;
import com.easyshare.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;
    @Autowired
    private LikeService likeService;

    @Override
    @Transactional
    public Result createPost(Long userId, String content) {
        Post post = new Post();
        post.setContent(content);
        post.setAuthorId(userId);
        post.setPublishAt(LocalDateTime.now());
        post.setLastUpdate(LocalDateTime.now());
        postDao.addPost(post);
        return new Result(ResultCode.SUCCESS.getCode(), "发布成功", null);
    }

    @Override
    public Result getAllPostsOfOneUser(Long authorId, Long userId, Long lastId, Integer pageSize) {
        List<PostVo> posts = postDao.findPosts(authorId, null, lastId, pageSize);
        for (PostVo post : posts) {
            post.setIsLiked(likeService.getLikeStatus(post.getId(), userId));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取帖子成功", posts);
    }

    @Override
    public Result getHotPostsAtRandom(Long userId, Integer dateLimit, Integer pageSize) {
        List<PostVo> posts = postDao.getHotPostsAtRandom(dateLimit, pageSize);
        for (PostVo post : posts) {
            post.setIsLiked(likeService.getLikeStatus(post.getId(), userId));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取最新帖子成功", posts);
    }

    @Override
    public Result searchPosts(String keywords, Long userId, Long lastId, Integer pageSize) {
        List<PostVo> posts = postDao.findPosts(null, keywords, lastId, pageSize);
        for (PostVo post : posts) {
            post.setIsLiked(likeService.getLikeStatus(post.getId(), userId));
        }
        return new Result(ResultCode.SUCCESS.getCode(), "获取搜索结果成功", posts);
    }

    @Override
    public Result getPostByPostId(Long postId, Long userId) {
        PostVo post = postDao.findOnePost(postId);
        if (post == null) {
            return new Result(ResultCode.ERROR.getCode(), "该帖子不存在", null);
        }
        post.setIsLiked(likeService.getLikeStatus(postId, userId));
        return new Result(ResultCode.SUCCESS.getCode(), "获取帖子成功", post);
    }

    @Override
    @Transactional
    public Result updatePost(Long userId, UpdatePostDto updatePostDto) {
        PostVo post = postDao.findOnePost(updatePostDto.getId());
        if (post == null) {
            return new Result(ResultCode.ERROR.getCode(), "帖子不存在，无法修改内容", null);
        }
        if (!post.getAuthorId().equals(userId)) {
            return new Result(ResultCode.ERROR.getCode(), "非作者不得修改内容", null);
        }
        postDao.updatePostContent(updatePostDto.getId(), updatePostDto.getContent(), LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "内容修改成功", null);
    }

    @Override
    @Transactional
    public Result deletePost(Long userId, Long postId) {
        PostVo post = postDao.findOnePost(postId);
        if (post == null) {
            return new Result(ResultCode.ERROR.getCode(), "内容不存在，无法删除", null);
        }
        if (!post.getAuthorId().equals(userId)) {
            return new Result(ResultCode.ERROR.getCode(), "非作者不得删除帖子", null);
        }
        postDao.deletePost(postId, LocalDateTime.now());
        return new Result(ResultCode.SUCCESS.getCode(), "删除帖子成功", null);
    }

}
