package com.myblog.bloggingapp.services;

import java.util.List;


import com.myblog.bloggingapp.payloads.PostDto;
import com.myblog.bloggingapp.payloads.PostResponse;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update

    PostDto updatePost(PostDto postDto,Integer postId);

    //delete

    void deletePost(Integer postId);

    // get all posts

    PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy);

    //get a post

    PostDto getPostById(Integer postId);

    // get all posts by category

    List<PostDto> getPostByCategory(Integer categoryId);

    // get all posts by users 

    List<PostDto> getPostByUser(Integer userId);

    // search posts

    List<PostDto> searchPosts(String keyword);

}
