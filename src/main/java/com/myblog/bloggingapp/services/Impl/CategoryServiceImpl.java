package com.myblog.bloggingapp.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblog.bloggingapp.entities.Category;
import com.myblog.bloggingapp.exceptions.ResourceNotFoundException;
import com.myblog.bloggingapp.payloads.CategoryDto;
import com.myblog.bloggingapp.repositories.CategoryRepo;
import com.myblog.bloggingapp.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Autowired
    private ModelMapper modelMapper ;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // TODO Auto-generated method stub

        Category catObj = this.modelMapper.map(categoryDto,Category.class);
        Category added = this.categoryRepo.save(catObj);



        return this.modelMapper.map(added,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        // TODO Auto-generated method stub
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
        this.categoryRepo.delete(cat);


        
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        // TODO Auto-generated method stub
        List<Category>categories = this.categoryRepo.findAll();
        List<CategoryDto>cato = categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());

        return cato;
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        // TODO Auto-generated method stub
             Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
             
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        // TODO Auto-generated method stub

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
        
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updated = this.categoryRepo.save(cat);
        return this.modelMapper.map(updated,CategoryDto.class);
    }
    
}
