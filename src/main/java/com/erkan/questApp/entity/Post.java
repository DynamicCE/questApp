package com.erkan.questApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    Long id;

    Long userId;

    String title;

    String status;

    @ManyToOne
    @JoinColumn(name="user_id",updatable = false,insertable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;




}