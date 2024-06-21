package com.erkan.questApp.repository;

import com.erkan.questApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface UserRepository extends JpaRepository<User,Long> {
}
