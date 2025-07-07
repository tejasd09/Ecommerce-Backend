package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.JWTService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.WishListService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private JWTService service;

    @Autowired
    private UserService userService;

    @Autowired
    private WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) throws SignatureException {

        try {
            if(token.startsWith("Bearer ")){
                token=token.substring(7);
            }

            //Validating the token to ensure to which user's wishList we need to add product
            String username = service.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            if(!service.validateToken(token, userDetails))
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }

            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if(wishListService.isProductAlreadyPresent(user,product))
            {
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The product is already added in wishlist");
            }
            wishListService.addProduct(user, product);
            return new ResponseEntity<>("Product Added to WishList Successfully...!!", HttpStatus.OK);


        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
        }catch(Exception e){
            e.getMessage();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
    }


    @GetMapping("/get")
    public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String token)throws SignatureException
    {
        try {
            System.out.println("Received Token :" + token);
            if(token.startsWith("Bearer ")){
                token=token.substring(7);
            }
            System.out.println("After removing bearer :" + token );

            String username = service.extractUsername(token);
            System.out.println("Extracted UserName :" + username);

            UserDetails userDetails = userService.loadUserByUsername(username);
            boolean b=service.validateToken(token, userDetails);
            System.out.println("isValid: " + b);
            if(!b){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }

            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            System.out.println(user.getId());
            List<ProductDto> products = wishListService.getProducts(user);
            return ResponseEntity.ok(products);


        }catch(ExpiredJwtException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token has expired");
        }catch(Exception e)
        {
            e.printStackTrace();

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT..!!!");
    }

}