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

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.service.CommentServiceImpl;

@RestController
@RequestMapping("/blog/post")
public class CommentController {
    
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/{postId}/comment")
    public List<CommentDTO> findAllCommentsByPost(@PathVariable(value="postId") Long postId){
        return commentService.findAllCommentsByPost(postId);
    }

    @GetMapping("/{postId}/comment/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable(value="postId") Long postId, @PathVariable(value="id") Long commentId){

        CommentDTO commentDTO = commentService.findByCommentId(postId, commentId);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value="postId") Long postId, @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/comment/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable(value="postId") Long postId, @Valid @RequestBody CommentDTO commentDTO, @PathVariable(value="Id") Long commentId){

        commentService.updateComment(postId, commentId, commentDTO);

        return new ResponseEntity<>("comment update success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comment/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable(value="postId") Long postId, @PathVariable(value="id") Long commentId){

        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("comment delet success", HttpStatus.OK);

    }

}
