package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.Result;
import com.erkan.questApp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    DataResult<Comment> getCommentById(Long id);

    Result createComment(Comment comment);

    Result updateComment(Long id, Comment comment);

    DataResult<Page<Comment>> getAllComments(Pageable pageable);

    DataResult<Page<Comment>> getCommentsByPostId(Long postId, Pageable pageable);

    DataResult<Page<Comment>> getCommentsByUserId(Long userId, Pageable pageable);

    Result softDeleteCommentById(Long id);

    // Sadece yorum durumlarını yönetmek istiyorsanız bunu dahil edin
    // Result changeCommentStatus(Long id, Comment.Status newStatus);
}