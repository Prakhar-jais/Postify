package com.myblog.bloggingapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myblog.bloggingapp.entities.Category;
import com.myblog.bloggingapp.entities.Post;
import com.myblog.bloggingapp.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer> {
    
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key")String title);
}
