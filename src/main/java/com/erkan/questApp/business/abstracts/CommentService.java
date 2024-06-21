package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.DataResult;
import com.erkan.questApp.entity.Comment;

import java.util.List;

public interface CommentService {
    DataResult<Comment> getCommentById(Long id);
    DataResult<Comment> createComment(Comment comment);
    DataResult<Comment> updateComment(Comment comment);
    DataResult<List<Comment>> getAllComments();
    DataResult<List<Comment>> getCommentsByPostId(Long postId);
    DataResult<List<Comment>> getCommentsByUserId(Long userId);
    DataResult<Comment> deleteCommentById(Long id);
}