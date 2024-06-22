package com.erkan.questApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column(name = "id")
    Long id;

    String userName;

    String password;

    String status;
@OneToMany(mappedBy = "User",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "User")
private List<Comment> comments;
}

