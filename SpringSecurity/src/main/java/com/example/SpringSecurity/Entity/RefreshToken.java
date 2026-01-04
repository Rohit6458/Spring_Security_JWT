package com.example.SpringSecurity.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RefreshToken extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
