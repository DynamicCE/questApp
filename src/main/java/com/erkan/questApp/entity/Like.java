package com.erkan.questApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Like {
    @Id
    Long id;

    Long userId;

    Long postId;

    String status;

    @ManyToOne
    @JoinColumn(name="id",updatable = false,insertable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="id",updatable = false,insertable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="id",updatable = false,insertable = false)
    private User user;
}
