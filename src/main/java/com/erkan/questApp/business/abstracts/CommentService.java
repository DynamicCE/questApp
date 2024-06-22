package com.erkan.questApp.business.abstracts;


import com.erkan.questApp.business.concretes.CommentManager;
import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.Result;
import com.erkan.questApp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    DataResult<Comment> getCommentById( Long id);

    Result createComment( Comment comment);

    Result updateComment(Comment comment);

    DataResult<Page<Comment>> getAllComments(Pageable pageable);

    DataResult<Page<Comment>> getCommentsByPostId(Long postId, Pageable pageable);

    DataResult<Page<Comment>> getCommentsByUserId(Long userId, Pageable pageable);

    Result softDeleteCommentById(Long id);

    Result changeCommentStatus(Long id, CommentManager.Status newStatus);
}
