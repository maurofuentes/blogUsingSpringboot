package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    
    private String usernameOrEmail;
    private String password;


}
