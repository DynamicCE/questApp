package com.erkan.questApp.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostDto{
    private Long id;
    private Long userId;
    private String title;
    private String status;
    private List<Long> likeIds;    // Like entity'sinin id'lerini tutar
    private List<Long> commentIds; // Comment entity'sinin id'lerini tutar
}
