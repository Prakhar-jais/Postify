package com.myblog.bloggingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.bloggingapp.payloads.ApiResponse;
import com.myblog.bloggingapp.payloads.CommentDto;
import com.myblog.bloggingapp.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService ;


    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){

        CommentDto commentGet = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(commentGet,HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true),HttpStatus.OK);
    }
}
