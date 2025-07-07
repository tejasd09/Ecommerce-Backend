package com.ecommerce.repository;

import com.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
