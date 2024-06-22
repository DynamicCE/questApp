package com.erkan.questApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Like {
    @Id
    Long id;

    Long userId;
    Long postId;
    Long commentId;

    String status;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private Comment comment;
}
