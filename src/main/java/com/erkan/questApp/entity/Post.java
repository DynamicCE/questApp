package com.erkan.questApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


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
}