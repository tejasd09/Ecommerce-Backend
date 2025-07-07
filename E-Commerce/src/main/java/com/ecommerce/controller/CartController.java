package com.ecommerce.controller;

import com.ecommerce.dto.CartDto;
import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.dto.CartSummaryDto;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.JWTService;
import com.ecommerce.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JWTService service;

    @Autowired
    private UserService userService;

    //post cart api
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDto cartDto, @RequestHeader("Authentication") String token)throws SignatureException
    {
        try{
            if(token.startsWith("Bearer "))
            {
                token=token.substring(7);
            }

            String userName=service.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(userName);

            if(!service.validateToken(token,userDetails))
            {
                return new ResponseEntity<>("Invalid Token " ,HttpStatus.UNAUTHORIZED);
            }

            User user= userService.findByUsername(userName);
            if(user==null)
            {
                return new ResponseEntity<>("User not found" ,HttpStatus.NOT_FOUND);
            }

            boolean b = cartService.addToCart(user, cartDto);
            if(b)
            {
                return new ResponseEntity<>("Product successfully added to cart",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
            }
        }
        catch(ExpiredJwtException e)
        {
           return new ResponseEntity<>("JWT token is expired" ,HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e)
        {
           e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error" , HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    //get all cart items
    @GetMapping("/get")
    public ResponseEntity<?> getCartItems(@RequestHeader("Authorization") String token)throws SignatureException
    {
        try{
            if(token.startsWith("Bearer "))
            {
                token=token.substring(7);
            }
            String userName=service.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(userName);

            if(!service.validateToken(token,userDetails))
            {
                return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
            }

            User user=userService.findByUsername(userName);
            if(user==null)
            {
                return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
            }
            CartSummaryDto cartItems = cartService.getCartItems(user);
            return ResponseEntity.ok(cartItems);


        }catch(ExpiredJwtException e)
        {
            return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
        }catch(Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //delete item from cart
    @DeleteMapping("/delete/{cart_id}")
    public ResponseEntity<?> removeItem(@RequestHeader("Authentication") String token,@PathVariable Integer cart_id)throws SignatureException
    {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            String userName = service.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(userName);

            if (!service.validateToken(token, userDetails)) {
                return new ResponseEntity<>("Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }

            User user=userService.findByUsername(userName);
            if(user==null)
            {
                return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
            }

            boolean b = cartService.removeItem(user, cart_id);
            if(b)
            {
                return new ResponseEntity<>("Successfully deleted from cart",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Product Not found or Unauthorized access",HttpStatus.NOT_FOUND);
            }


        }catch(JwtException e)
        {
            return new ResponseEntity<>("Invalid token",HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error" , HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
