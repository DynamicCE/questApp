package com.erkan.questApp.entity.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private Long commentId;
    private String status;
}
