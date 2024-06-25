package com.erkan.questApp.repository;

import com.erkan.questApp.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface LikeRepository extends JpaRepository<Like,Long> {
    Page<Like> findByCommentId ( Long commentId, Pageable pageable );

    Page<Like> findByPostId ( Long postId, Pageable pageable );

    Page<Like> findByUserId ( Long userId, Pageable pageable );
}
