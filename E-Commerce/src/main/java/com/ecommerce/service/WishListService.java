package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.WishList;
import com.ecommerce.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class WishListService {

    @Autowired
    private WishListRepo wishListRepo;



    @Autowired
    private ProductService productService;

    public void addProduct(User user, Product product) {

        WishList wishList=new WishList(user, product);//The properties of WishList will be stored in database
        wishListRepo.save(wishList);
    }


    public boolean isProductAlreadyPresent(User user, Product product) {
        return wishListRepo.existsByUserAndProduct(user,product);
    }

    public List<ProductDto> getProducts(User user) {
        //Fetch All wishList entries/items
         List<WishList> wishLists= wishListRepo.findAllByUser(user);


         List<ProductDto> productDtos=new ArrayList<>();
         for(WishList wishList: wishLists)
         {
             //From wishList we get product , converted product into product dto, and added
             productDtos.add(productService.getProductDto(wishList.getProduct()));
         }
        System.out.println(productDtos);
         return productDtos;




    }
}
