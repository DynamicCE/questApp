package com.erkan.questApp.repository;

import com.erkan.questApp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public
interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId ( Long postId );
}
