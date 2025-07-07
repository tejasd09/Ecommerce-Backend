package com.ecommerce.dto;

import jakarta.persistence.Entity;
import lombok.NonNull;


public class ProductDto {

    private Integer productId;
    private @NonNull String name;
    private @NonNull String imageUrl;
    private @NonNull double price;
    private @NonNull String description;
    private @NonNull Integer category_id;

    public ProductDto() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer id) {
        this.productId = id;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public @NonNull String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public @NonNull Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(@NonNull Integer category_id) {
        this.category_id = category_id;
    }




}
