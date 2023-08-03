package com.myblog.bloggingapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.bloggingapp.payloads.ApiResponse;
import com.myblog.bloggingapp.payloads.CategoryDto;
import com.myblog.bloggingapp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService ;
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid@RequestBody CategoryDto categoryDto){
        CategoryDto created = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(created, HttpStatus.CREATED);
    }

    //update 
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer id){
        CategoryDto updated = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<CategoryDto>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully", true), HttpStatus.OK);
    }
    // get
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer id){
        CategoryDto getCat = this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDto>(getCat,HttpStatus.OK);
    }
    //get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> ls = this.categoryService.getAllCategory();
        return ResponseEntity.ok(ls);
    }
    
}
