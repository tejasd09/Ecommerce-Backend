package com.ecommerce.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="cart")
public class Cart {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cart_id;

     @Column(name="created_Date")
    private Date date;

     @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

     @ManyToOne
    @JoinColumn(name="id")
    private User user;

     private Integer quantity;

    public Cart() {
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
