package com.erkan.questApp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public
class Comment {
    @Id
    Long id;

    Long postId;
    Long userId;
    String text;

    String status;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;


}
