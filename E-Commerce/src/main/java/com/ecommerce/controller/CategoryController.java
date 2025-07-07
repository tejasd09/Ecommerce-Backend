package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("/create")
    public String createCategory(@RequestBody Category category)
    {
        categoryService.createCat(category);
        return "Success";
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCategory()
    {
        List<Category> l1=categoryService.getCat();
        return new ResponseEntity<>(l1, HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestBody Category updateCategory)
    {
        if(categoryService.updateCat(categoryId, updateCategory))
        {
            return new ResponseEntity<>("Updated Successfully...!!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not found....!!",HttpStatus.NOT_FOUND);

    }




}
