package com.example.SpringSecurity.Service;

import com.example.SpringSecurity.DTO.UserDTO;
import com.example.SpringSecurity.Entity.RefreshToken;
import com.example.SpringSecurity.Entity.User;
import com.example.SpringSecurity.Repository.RefreshTokenRepository;
import com.example.SpringSecurity.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private  final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public Boolean createUser(UserDTO userDTO) {
        User user=modelMapper.map(userDTO,User.class);
        user.setPassword(encodePassword(user.getPassword()));
        user=userRepository.save(user);
        log.info("User Created Successfully {} ",user.getUsername());
        return true;
    }

    /*
    * Encode Password
    * */
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        // Return User Date To UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    public void refreshToken(String token,String username){
        User user=userRepository.findByUsername(username);
        boolean refreshTokenExists =refreshTokenRepository.existsByUser(user);
        if(!(refreshTokenExists)){
            RefreshToken refreshToken=new RefreshToken();
            refreshToken.setToken(token);
            refreshToken.setUser(user);
            refreshTokenRepository.save(refreshToken);
            log.info("Refresh Token Created Successfully {} ",user.getUsername());
        }
        else{
            com.example.SpringSecurity.Entity.RefreshToken refreshToken =refreshTokenRepository.findByUser(user);
            refreshToken.setToken(token);
            refreshTokenRepository.save(refreshToken);
            log.info("Refresh Token Updated Successfully {} ",user.getUsername());
        }
    }

}
