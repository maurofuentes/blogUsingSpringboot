package com.springboot.blog.controller;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.LoginDTO;
import com.springboot.blog.dto.RegisterUserDTO;
import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.IRoleRepository;
import com.springboot.blog.repository.IUserRepository;

@RestController
@RequestMapping("/blog/post/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("login success", HttpStatus.OK);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO){

        if(userRepository.existsByUsername(registerUserDTO.getUsername())){

            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerUserDTO.getEmail())){

            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setUsername(registerUserDTO.getUsername());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>("successfully registered user", HttpStatus.OK);
    }
}
