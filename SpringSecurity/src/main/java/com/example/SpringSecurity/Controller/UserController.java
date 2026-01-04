package com.example.SpringSecurity.Controller;

import com.example.SpringSecurity.DTO.LoginRequest;
import com.example.SpringSecurity.DTO.Token;
import com.example.SpringSecurity.DTO.UserDTO;
import com.example.SpringSecurity.Entity.RefreshToken;
import com.example.SpringSecurity.Service.JwtService;
import com.example.SpringSecurity.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final AuthenticationManager authManager;

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    public UserController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody UserDTO userDTO){
        Boolean UserStatus=userService.createUser(userDTO);
        if(UserStatus){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/login")
    public Token login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        log.info("Login Successful & Token generation process started");
        if (authentication.isAuthenticated()) {
            Token token = new Token();
            token.setACCESS_TOKEN(jwtService.generateToken(loginRequest.getUsername()));
            token.setRefresh_TOKEN(jwtService.refreshToken(loginRequest.getUsername()));
            userService.refreshToken(token.getRefresh_TOKEN(),loginRequest.getUsername());
            return token;
        } else {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    @GetMapping("/refresh")
    public String  refreshToken(@RequestBody Token token){
        String username=jwtService.extractUsername(token.getRefresh_TOKEN());
        Boolean refreshToken=jwtService.validateToken(token.getRefresh_TOKEN(),username);
        if(refreshToken){
            log.info("Refresh Token is valid, We are ready to used as Access Token");
            return token.getRefresh_TOKEN();
        }
        else{
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
