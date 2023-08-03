package com.myblog.bloggingapp.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblog.bloggingapp.entities.Category;
import com.myblog.bloggingapp.entities.Post;
import com.myblog.bloggingapp.entities.User;
import com.myblog.bloggingapp.exceptions.ResourceNotFoundException;
import com.myblog.bloggingapp.payloads.PostDto;
import com.myblog.bloggingapp.payloads.PostResponse;
import com.myblog.bloggingapp.repositories.CategoryRepo;
import com.myblog.bloggingapp.repositories.PostRepo;
import com.myblog.bloggingapp.repositories.UserRepo;
import com.myblog.bloggingapp.services.PostService;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;
    
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        // we only have title and content 
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post created = this.postRepo.save(post);

        return this.modelMapper.map(created,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
        this.postRepo.delete(post);
        
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy) {
        
        

        Pageable p = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).ascending()) ;

        Page<Post>pagePosts = this.postRepo.findAll(p);
        List<Post>allPosts = pagePosts.getContent();

        List<PostDto>posts = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());

        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));
        List<Post>posts = this.postRepo.findByCategory(cat);
        List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id", postId));
        
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
        List<Post>posts = this.postRepo.findByUser(user);
        List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return  postDtos ;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        
        // User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
        // UserDto userDto = this.modelMapper.map(user,UserDto.class);
        // Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
        // CategoryDto categoryDto = this.modelMapper.map(category,CategoryDto.class);
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        // post.setCategory(postDto.getCategory(categoryDto));
        // post.setUser(postDto.getUser(userDto));
        Post updated = this.postRepo.save(post);
        return this.modelMapper.map(updated, PostDto.class);
    }
    
}
