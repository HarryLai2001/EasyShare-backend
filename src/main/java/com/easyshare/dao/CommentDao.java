package com.easyshare.dao;

import com.easyshare.pojo.po.Comment;
import com.easyshare.pojo.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    public void addComment(Comment comment);

    public CommentVo findOneComment(@Param("commentId") Long commentId);

    public List<CommentVo> findCommentsOfOnePost(@Param("postId") Long postId);

    public void deleteComment(@Param("commentId") Long commentId);
}
