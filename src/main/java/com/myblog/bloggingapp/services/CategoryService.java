package com.myblog.bloggingapp.services;


import java.util.List;

import com.myblog.bloggingapp.payloads.CategoryDto;

public interface CategoryService {
    
    // create

    public CategoryDto createCategory(CategoryDto categoryDto);

    // update
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    // delete 
    public void deleteCategory(Integer categoryId);

    public CategoryDto getCategory(Integer categoryId);

    //get
    
    public List<CategoryDto>getAllCategory();

}
