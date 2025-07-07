package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;




    public void save(ProductDto productDto, Category category) {
        //Now as we have category id , we can save the details of request body of productdto to product entity
        Product product=new Product();
        product.setDescription(productDto.getDescription());
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);


        productRepo.save(product);

    }

    public ProductDto getProductDto(Product product)
    {
        ProductDto productDto=new ProductDto();
        productDto.setCategory_id(product.getCategory().getId());
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        return productDto;

    }

    public List<ProductDto> getProducts() {
        List<Product> allProducts=productRepo.findAll();
        List<ProductDto> productDtos=new ArrayList<>();

        for(Product product:allProducts)
       {
           productDtos.add(getProductDto(product));
       }
        return productDtos;
    }

    public boolean updateProduct(Integer updateId, ProductDto productDto, Category category) {

        //Need to fetch the data of given id for updating;
        Optional<Product> optionalProduct=productRepo.findById(updateId);


        if(optionalProduct.isEmpty())
        {
            return false;
        }

//        Product product=new Product();//It will create new Product but we want to update existing product which is found by using id

        Product product=optionalProduct.get();

        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);



        productRepo.save(product);
        return true;

    }

    public boolean isDeleted(Integer deleteId)
    {
        Optional<Product> isPresent=productRepo.findById(deleteId);

        if(isPresent.isEmpty())
        {
            return false;
        }
        productRepo.deleteById(deleteId);
        return true;

    }

    public Product findById(Integer productId)
    {
        Optional<Product> optionalProduct=productRepo.findById(productId);
        if(optionalProduct.isEmpty())
        {
            System.out.println("Product id is Invalid : " + productId);
        }
        return optionalProduct.get();
    }
}
