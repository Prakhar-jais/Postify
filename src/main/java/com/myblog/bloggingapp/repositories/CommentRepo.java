package com.myblog.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.bloggingapp.entities.Comment;

public interface CommentRepo  extends JpaRepository<Comment,Integer>{
    
}
