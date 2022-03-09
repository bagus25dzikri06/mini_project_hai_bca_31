package com.simple_form.simple_form.repository;

import com.simple_form.simple_form.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    
}
