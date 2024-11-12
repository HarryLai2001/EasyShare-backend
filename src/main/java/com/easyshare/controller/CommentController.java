package com.easyshare.controller;

import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.CommentDto;
import com.easyshare.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public Result createComment(@RequestJwtClaim("id") Long userId,
                                @Validated @RequestBody CommentDto commentDto) {
        return commentService.createComment(userId, commentDto);
    }

    @GetMapping("/comments")
    public Result getHieraCommentsOfOnePost(@RequestParam("postId") Long postId) {
        return commentService.getHieraCommentsOfOnePost(postId);
    }

    @DeleteMapping("/delete")
    public Result deleteComment(@RequestJwtClaim("id") Long userId,
                                @RequestParam("commentId") Long commentId) {
        System.out.println(userId);
        System.out.println(commentId);
        return commentService.deleteComment(userId, commentId);
    }
}
