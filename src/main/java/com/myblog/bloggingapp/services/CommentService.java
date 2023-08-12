package com.myblog.bloggingapp.services;

import com.myblog.bloggingapp.payloads.CommentDto;

public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
