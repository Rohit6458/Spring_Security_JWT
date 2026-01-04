package com.example.SpringSecurity.DTO;

import lombok.Data;

@Data
public class Token {
    private String ACCESS_TOKEN;
    private String Refresh_TOKEN;
}
