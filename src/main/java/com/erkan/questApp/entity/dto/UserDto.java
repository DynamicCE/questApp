package com.erkan.questApp.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String status;
    private List<Long> postIds;
    private List<Long> commentIds;
    private List<Long> likeIds;
}
