package com.example.SpringSecurity.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role;
}
