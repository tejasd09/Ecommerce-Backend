package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto)
    {
        //Since request body of productDto contains category id we need to check wether the
        //category is created with this id or not , if category id present then go for adding else return bad request


        Optional<Category> optionalCategory=categoryRepo.findById(productDto.getCategory_id());
        if(!optionalCategory.isPresent())
        {
            return new ResponseEntity<>("Category Id incorrect",HttpStatus.BAD_REQUEST);
        }
        productService.save(productDto,optionalCategory.get());
        return new ResponseEntity<>("Product added SuccessFully...!!",HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProduct()
    {
        List<ProductDto> productDto=productService.getProducts();
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @PutMapping("/update/{updateId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer updateId ,@RequestBody ProductDto productDto)
    {

        Optional<Category> optionalCategory=categoryRepo.findById(productDto.getCategory_id());

        if(!optionalCategory.isPresent())
        {
            return new ResponseEntity<>("Category Id incorrect",HttpStatus.BAD_REQUEST);
        }

        if(productService.updateProduct(updateId,productDto,optionalCategory.get()))
        {
            return new ResponseEntity<>("Product updated SuccessFully...!!",HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid update(product) id",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{deleteId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer deleteId)
    {
        if(productService.isDeleted(deleteId))
        {
            return new ResponseEntity<>("Product Deleted SuccessFully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Id ",HttpStatus.NOT_FOUND);
    }








}
