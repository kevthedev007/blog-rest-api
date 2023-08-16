package com.springbooot.blogrestapi.service.impl;

import com.springbooot.blogrestapi.dto.LoginDto;
import com.springbooot.blogrestapi.dto.RegisterDto;
import com.springbooot.blogrestapi.enums.Role;
import com.springbooot.blogrestapi.exception.BlogException;
import com.springbooot.blogrestapi.model.User;
import com.springbooot.blogrestapi.repository.UserRepository;
import com.springbooot.blogrestapi.security.JwtTokenProvider;
import com.springbooot.blogrestapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public String registerUser(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Email already exists: " + registerDto.getEmail());
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
        return "User Registered Successfully";
    }


    @Override
    public String LoginUser(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDto.getEmail(),
               loginDto.getPassword()
       ));

       SecurityContextHolder.getContext().setAuthentication(authentication);

       String token = jwtTokenProvider.generateToken(authentication);

       return token;
    }
}
