package com.erkan.questApp.entity.dto;


import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String text;
    private String status;
    private int likeCount; // likes listesinin boyutunu göstermek için
}
