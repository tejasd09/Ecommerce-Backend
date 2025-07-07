package com.ecommerce.service;

import com.ecommerce.dto.CartDto;
import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.dto.CartSummaryDto;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepo cartRepo;

    public boolean addToCart(User user, CartDto cartDto) {
        Product product= productService.findById(cartDto.getProductId());
        if(product==null)
        {
            return false;
        }

        Cart cart=new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(cartDto.getQuantity());
        cart.setCart_id(cart.getCart_id());
        cart.setDate(new Date());

        cartRepo.save(cart);
        return true;
    }

    public CartResponseDto getCartDto(Cart cart)
    {
        Integer cartId=cart.getCart_id();
        Product product=cart.getProduct();
        Integer quantity=cart.getQuantity();
        double price=product.getPrice()*quantity;

        CartResponseDto cartResponseDto=new CartResponseDto(cartId,product,quantity,price);
        return cartResponseDto;

    }

    public CartSummaryDto getCartItems(User user) {
        List<Cart> carts = cartRepo.findAllByUserOrderByDateDesc(user);
        List<CartResponseDto> cartResponseDtos=new ArrayList<>();
        double totalcost=0.0;

        for(Cart c:carts)
        {
            cartResponseDtos.add(getCartDto(c));
            totalcost+=getCartDto(c).getPrice();
        }
        return new CartSummaryDto(cartResponseDtos,totalcost);
    }

    public boolean removeItem(User user,Integer cart_id)
    {

        Optional<Cart> cart = cartRepo.findById(cart_id);

        if(!cart.isPresent())
        {
            System.out.println("Product not found in cart");
            return false;
        }

        Cart cart1 = cart.get();

        if(cart1.getUser().getId()!=user.getId())
        {
            System.out.println("Unauthorized : This cart item does not belong to user id : " + cart1.getUser().getId());
            return false;
        }

        cartRepo.deleteById(cart_id);
       return true;

    }
}
