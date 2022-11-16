package com.springboot.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.exception.BlogAppException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.ICommentRespository;
import com.springboot.blog.repository.IPostRepository;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRespository commentRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

        Comment commentToSave = this.mappingDtoToEntity(commentDTO);

        Post postOptional = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found", postId));

        commentToSave.setPost(postOptional);

        Comment savedComment = commentRepository.save(commentToSave);

        return this.mappingEntityToDto(savedComment);
    }

    @Override
    public List<CommentDTO> findAllCommentsByPost(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> this.mappingEntityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO findByCommentId(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment not found", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "the comment does not belong to indicated post");
        }

        return this.mappingEntityToDto(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment not found", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "the comment does not belong to indicated post");
        }

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setContent(commentDTO.getContent());

        Comment savedComment = commentRepository.save(comment);

        return this.mappingEntityToDto(savedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post postDTO = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment not found", commentId));

        if (!comment.getPost().getId().equals(postDTO.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "the comment does not belong to indicated post");
        }

        commentRepository.delete(comment);

    }

    private CommentDTO mappingEntityToDto(Comment comment) {

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        return commentDTO;
    }

    private Comment mappingDtoToEntity(CommentDTO commentDTO) {

        Comment comment = modelMapper.map(commentDTO, Comment.class);

        return comment;
    }

    /** 
     * THIS IS ANOTHER WAY TO MAP ENTITY TO DTO AND VICEVERSA *
     **/

    // private Comment mappingDtoToEntity(CommentDTO commentDTO) {
    // Comment newComment = new Comment();

    // newComment.setId(commentDTO.getId());
    // newComment.setName(commentDTO.getName());
    // newComment.setEmail(commentDTO.getEmail());
    // newComment.setContent(commentDTO.getContent());

    // return newComment;
    // }

    // private CommentDTO mappingEntityToDto(Comment comment) {

    // CommentDTO commentDTO = new CommentDTO();

    // commentDTO.setId(comment.getId());
    // commentDTO.setName(comment.getName());
    // commentDTO.setEmail(comment.getEmail());
    // commentDTO.setContent(comment.getContent());

    // return commentDTO;
    // }

    /** 
     * THIS IS ANOTHER WAY TO MAP ENTITY TO DTO AND VICEVERSA * - END - 
     **/
}
