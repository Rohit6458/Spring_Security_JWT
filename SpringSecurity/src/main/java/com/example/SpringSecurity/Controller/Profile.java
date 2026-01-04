package com.example.SpringSecurity.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class Profile {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminProfile")
    public String profile(){
        log.info("Admin User Profile");
        return "Admin Profile";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/userProfile")
    public String user(){
        log.info("User Profile");
        return "User Profile";
    }

    @PreAuthorize("hasRole('ADMIN,USER')")
    @GetMapping("/Dashboard")
    public String dashBoard(){
        return "Welcome to Dashboard";
    }
}
