package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.service.PostServiceImpl;

@RestController
@RequestMapping("/blog/post")
public class PostController {
    
    @Autowired
    private PostServiceImpl postService;

    @GetMapping
	public List<PostDTO> findAll(){
		
		return postService.findAll();
	}

    @GetMapping("/{id}")
	public ResponseEntity<PostDTO> findById(@PathVariable("id") Long id) {
		
		PostDTO postDTO = postService.findById(id);
		
		if(postDTO == null) {
			throw new ResourceNotFoundException("post not found", id);
		}
		
		return new ResponseEntity<>(postService.findById(id), HttpStatus.OK) ;
	}
	
	@PostMapping
	public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO) {
		
		return new ResponseEntity<>(postService.create(postDTO), HttpStatus.CREATED) ;
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody PostDTO postDTO) {
		
		postService.update(postDTO);
		
		return new ResponseEntity<>("post update success", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		
		PostDTO post = postService.findById(id);
		
		if(post == null) {
			throw new ResourceNotFoundException("post not found", id);
		}
		
		postService.delete(id);
		
		return new ResponseEntity<>("post delete success", HttpStatus.OK);
	}
}
