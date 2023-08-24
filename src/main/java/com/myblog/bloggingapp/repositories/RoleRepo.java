package com.myblog.bloggingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.bloggingapp.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    
}
