package com.erkan.questApp.repository;

import com.erkan.questApp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface LikeRepository extends JpaRepository<Like,Long> {
}
