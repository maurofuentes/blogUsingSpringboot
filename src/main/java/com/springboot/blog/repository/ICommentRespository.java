package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.model.Comment;

public interface ICommentRespository extends JpaRepository<Comment,Long>{
    
    public List<Comment> findByPostId(Long postId);
}
