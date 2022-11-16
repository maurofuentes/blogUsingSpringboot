package com.springboot.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.springboot.blog.model.Comment;

public class PostDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "The post title should have at least 2 characters")
    private String title;

    @NotEmpty(message = "The post subject should not be empty")
    private String subject;

    @NotEmpty
    @Size(min = 10, message = "The post description should have at least 10 characters")
    private String description;

    private Set<Comment> comments;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String subject, String description, Set<Comment> comments) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}