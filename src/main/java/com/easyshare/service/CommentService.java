package com.easyshare.service;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.dto.CommentDto;

public interface CommentService {
    public Result createComment(Long userId, CommentDto commentDto);
    public Result getHieraCommentsOfOnePost(Long postId);
    public Result deleteComment(Long userId, Long commentId);
}
