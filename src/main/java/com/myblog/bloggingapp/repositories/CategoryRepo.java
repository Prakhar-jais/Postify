package com.myblog.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.bloggingapp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    
}
