package com.myblog.bloggingapp.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblog.bloggingapp.entities.Comment;
import com.myblog.bloggingapp.entities.Post;
import com.myblog.bloggingapp.exceptions.ResourceNotFoundException;
import com.myblog.bloggingapp.payloads.CommentDto;
import com.myblog.bloggingapp.repositories.CommentRepo;
import com.myblog.bloggingapp.repositories.PostRepo;
import com.myblog.bloggingapp.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private PostRepo postRepo;


    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id",postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment saved = this.commentRepo.save(comment);
        return this.modelMapper.map(saved,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment id",commentId));
        commentRepo.delete(comment);

        
    }
    
}
