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

    String text;

    String status;

    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    private Post post ;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;


}
