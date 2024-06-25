package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.Result;
import com.erkan.questApp.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeService {

    DataResult<Like> getLikeById(Long id);

    Result createLike(Like like);

    Result updateLike(Long likeId, Like newLike);

    DataResult<Page<Like>> getAllLikes(Pageable pageable);

    DataResult<Page<Like>> getLikesByPostId(Long postId, Pageable pageable);

    DataResult<Page<Like>> getLikesByUserId(Long userId, Pageable pageable);

    Result softDeleteLikeById(Long id);

    DataResult<Page<Like>> getLikesByCommentId(Long commentId, Pageable pageable);
}