package com.erkan.questApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Like {
    @Id
    Long id;

    Long userId;

    Long postId;
}
