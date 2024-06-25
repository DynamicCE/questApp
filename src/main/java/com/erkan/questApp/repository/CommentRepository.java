package com.erkan.questApp.repository;

import com.erkan.questApp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndStatusNot(Long id, String status);
    Page<Comment> findAllByStatusNot(String status, Pageable pageable);
    Page<Comment> findByPostIdAndStatusNot(Long postId, String status, Pageable pageable);
    Page<Comment> findByUserIdAndStatusNot(Long userId, String status, Pageable pageable);

    Page<Comment> findByPostId ( Long postId, Pageable pageable );

    Page<Comment> findByUserId ( Long userId, Pageable pageable );
}