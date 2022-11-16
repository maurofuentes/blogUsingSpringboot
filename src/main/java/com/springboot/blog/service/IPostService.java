package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.PostDTO;
public interface IPostService {
    
    public PostDTO create(PostDTO post);

    public PostDTO update(PostDTO post);

    public PostDTO findById(Long id);

    public List<PostDTO> findAll();

    public void delete(Long id);
    
}
