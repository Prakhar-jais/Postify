package com.myblog.bloggingapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.bloggingapp.entities.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User,Integer> {
    
    Optional<User>findByEmail(String email);
}
