package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.CommentDTO;

public interface ICommentService {
    
    public CommentDTO createComment(Long postId, CommentDTO commentDTO);

    public List<CommentDTO> findAllCommentsByPost(Long postId);

    public CommentDTO findByCommentId(Long postId, Long commentId);

    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    public void deleteComment(Long postId, Long commentId);
}
