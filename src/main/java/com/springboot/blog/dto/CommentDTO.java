package com.springboot.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {

    private Long id;

    @NotEmpty(message = "The name should not be empty")
    private String name;

    @NotEmpty(message = "The email is required")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "The content of comment should have at least 10 characters")
    private String content;

    public CommentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
