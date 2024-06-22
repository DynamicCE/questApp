package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.entity.Like;

import java.util.List;

public interface LikeService {
    DataResult<Like> getLikeById(Long id);
    DataResult<Like> createLike(Like like);
    DataResult<List<Like>> getAllLikes();
    DataResult<List<Like>> getLikesByPostId(Long postId);
    DataResult<List<Like>> getLikesByUserId(Long userId);
    DataResult<Like> deleteLikeById(Long id);
}