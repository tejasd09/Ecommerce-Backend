package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Each user have one wishlist
    @ManyToOne  //Many wishlist entries can belong to one user
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne // Each wishList entry contains one product
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="created_date")
    private Date createdDate;

    public WishList()
    {

    }

    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate=new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
