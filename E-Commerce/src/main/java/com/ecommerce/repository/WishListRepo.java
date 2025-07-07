package com.ecommerce.repository;


import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepo extends JpaRepository<WishList, Integer> {


    boolean existsByUserAndProduct(User user, Product product);
    List<WishList> findAllByUser(User user);

}
