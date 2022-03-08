package com.simple_form.simple_form.repository;

import com.simple_form.simple_form.model.ProductCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryModel, Integer> {
    
}
