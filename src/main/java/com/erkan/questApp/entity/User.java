package com.erkan.questApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

