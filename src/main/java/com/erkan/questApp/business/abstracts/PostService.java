package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.entity.Post;

import java.util.List;

public interface PostService {
    DataResult<Post> getPostById(Long id);
    DataResult<Post> createPost(Post post);
    DataResult<Post> updatePost(Post post);
    DataResult<List<Post>> getAllPosts();
    DataResult<List<Post>> getPostsByUserId(Long userId);
    DataResult<Post> deletePostById(Long id);
}