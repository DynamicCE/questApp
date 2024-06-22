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

    String test;

    String status;

    @ManyToOne
    @JoinColumn(name="id",updatable = false,insertable = false)
    private User user;

    @OneToMany(mappedBy = "Post")
    private List<Like> likes;

    @OneToMany(mappedBy = "Post")
    private List<Comment> comments;




}