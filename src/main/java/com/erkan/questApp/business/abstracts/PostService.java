package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.Result;
import com.erkan.questApp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    DataResult<Post> getPostById(Long id);

    Result createPost(Post post);

    Result updatePost(Long postId, Post newPost);

    DataResult<Page<Post>> getAllPosts(Pageable pageable);

    DataResult<Page<Post>> getPostsByUserId(Long userId, Pageable pageable);

    Result softDeletePostById(Long id);

    DataResult<Post> getPostWithLikesAndComments(Long postId);

    Result updatePostTitle(Long postId, String newTitle);
}