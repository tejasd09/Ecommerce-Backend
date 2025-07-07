package com.ecommerce.dto;

import com.ecommerce.model.Product;

public class CartResponseDto {
    private Integer cartId;
    private Product product;
    private Integer quantity;
    private double price;

    CartResponseDto(){
    }

    public CartResponseDto(Integer cartId,Product product,Integer quantity, double price)
    {
        this.cartId=cartId;
        this.product=product;
        this.quantity=quantity;
        this.price=price;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
