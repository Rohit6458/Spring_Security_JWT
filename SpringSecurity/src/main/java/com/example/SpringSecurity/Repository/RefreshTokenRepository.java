package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.Entity.RefreshToken;
import com.example.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    boolean existsByUser(User user);

    RefreshToken findByUser(User user);
}
