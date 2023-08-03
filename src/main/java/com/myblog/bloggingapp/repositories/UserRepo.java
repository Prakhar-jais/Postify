package com.myblog.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.bloggingapp.entities.User;

public interface UserRepo extends JpaRepository<User,Integer> {
    
}
