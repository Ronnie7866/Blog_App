package com.amir.blog.Implementation;

import com.amir.blog.Entities.Category;
import com.amir.blog.Exceptions.ResourceNotFoundException;
import com.amir.blog.Payloads.CategoryDTO;
import com.amir.blog.Repositories.CategoryRepo;
import com.amir.blog.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category save = categoryRepo.save(category);
        return modelMapper.map(save, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category save = categoryRepo.save(category);
        return modelMapper.map(save, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> all = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category i : all) {
            CategoryDTO map = modelMapper.map(i, CategoryDTO.class);
            categoryDTOS.add(map);
        }
        return categoryDTOS;

//        return all.stream().map((category -> modelMapper.map(category, CategoryDTO.class))).collect(Collectors.toList());
    }
}
