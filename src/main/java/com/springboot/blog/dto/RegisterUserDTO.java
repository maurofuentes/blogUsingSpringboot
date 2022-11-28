package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDTO {
    
    private String name;
    private String username;
    private String email;
    private String password;
    
}
