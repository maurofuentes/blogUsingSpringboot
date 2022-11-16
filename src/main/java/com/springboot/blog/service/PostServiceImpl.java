package com.springboot.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.IPostRepository;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO create(PostDTO postDTO) {

        Post post = modelMapper.map(postDTO, Post.class);

        postRepository.save(post);

        PostDTO savePostDTO = modelMapper.map(post, PostDTO.class);

        return savePostDTO;
    }

    @Override
    public PostDTO findById(Long id) {

        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional == null) {

            throw new ResourceNotFoundException("post not found", id);

        }

        PostDTO foundedPostDTO = modelMapper.map(postOptional, PostDTO.class);

        return foundedPostDTO;
    }

    @Override
    public List<PostDTO> findAll() {

        // return postRepository.findAll();
        List<Post> postList = postRepository.findAll();

        return postList.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        postRepository.deleteById(id);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {

        Post post = postRepository.findById(postDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("post not found", postDTO.getId()));

        post.setTitle(postDTO.getTitle());
        post.setSubject(postDTO.getSubject());
        post.setDescription(postDTO.getDescription());
        post.setComments(postDTO.getComments());

        PostDTO savedPostDTO = modelMapper.map(postRepository.save(post), PostDTO.class);

        return savedPostDTO;
    }

}
