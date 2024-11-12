package com.easyshare.controller;

import com.easyshare.annotation.RequestBodyParam;
import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.UpdatePostDto;
import com.easyshare.service.PostService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public Result createPost(@RequestJwtClaim("id") Long userId,
                             @NotBlank @RequestBodyParam("content") String content) {
        return postService.createPost(userId, content);
    }

    @GetMapping("/posts")
    public Result getAllPostsOfOneUser(@RequestParam(value = "authorId", required = false) Long authorId,
                                       @RequestJwtClaim("id") Long userId,
                                       @RequestParam(value = "lastId", required = false) Long lastId,
                                       @RequestParam("pageSize") Integer pageSize) {
        if (authorId == null) { // 若authorId为空则默认为获取用户本人的帖子
            return postService.getAllPostsOfOneUser(userId, userId, lastId, pageSize);
        } else {
            return postService.getAllPostsOfOneUser(authorId, userId, lastId, pageSize);
        }
    }

    @GetMapping("/hot-posts")
    public Result getHotPosts(@RequestJwtClaim("id") Long userId,
                              @RequestParam("dateLimit") Integer dateLimit,
                              @RequestParam("pageSize") Integer pageSize) {
        return postService.getHotPostsAtRandom(userId, dateLimit, pageSize);
    }

    @GetMapping("/search")
    public Result searchPosts(@RequestParam("keywords") @NotBlank(message = "关键词不能为空") String keywords,
                              @RequestJwtClaim("id") Long userId,
                              @RequestParam(value = "lastId", required = false) Long lastId,
                              @RequestParam("pageSize") Integer pageSize) {
        return postService.searchPosts(keywords, userId, lastId, pageSize);
    }

    @GetMapping("/post-detail")
    public Result getPostById(@RequestParam("postId") Long postId,
                              @RequestJwtClaim("id") Long userId) {
        return postService.getPostByPostId(postId, userId);
    }

    @PutMapping("/update")
    public Result updatePost(@RequestJwtClaim("id") Long userId,
                             @Validated @RequestBody UpdatePostDto updatePostDto) {
        return postService.updatePost(userId, updatePostDto);
    }

    @DeleteMapping("/delete")
    public Result deletePost(@RequestJwtClaim("id") Long userId,
                             @RequestParam("postId") Long postId) {
        return postService.deletePost(userId, postId);
    }

}
