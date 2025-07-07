package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public void createCat(Category category)
    {
        categoryRepo.save(category);
    }

    public List<Category> getCat()
    {
        return categoryRepo.findAll();

    }


    public boolean updateCat(int categoryId, Category updateCategory) {

        if(categoryRepo.findById(categoryId).isPresent())
        {
            Category category=categoryRepo.getReferenceById(categoryId);
            category.setId(updateCategory.getId());
            category.setCategoryName(updateCategory.getCategoryName());
            category.setDescription(updateCategory.getDescription());
            categoryRepo.save(category);
            return true;
        }
        return false;


    }


}
