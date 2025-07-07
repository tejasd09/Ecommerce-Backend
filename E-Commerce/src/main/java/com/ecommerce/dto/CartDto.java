package com.ecommerce.dto;

import com.ecommerce.model.Product;
import jakarta.validation.constraints.NotNull;

public class CartDto {
    private Integer cart_id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;

    public Integer getCart_id() {
        return cart_id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
