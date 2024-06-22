package com.erkan.questApp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public
class Comment {
    @Id
    Long id;

    Long postId;

    String text;

    String status;

    @ManyToOne()
}
