package com.simple_form.simple_form.service;

import java.util.List;

import javax.transaction.Transactional;

import com.simple_form.simple_form.model.ProductCategoryModel;
import com.simple_form.simple_form.repository.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategoryModel> listAll() {
        return productCategoryRepository.findAll();
    }

    public void save(ProductCategoryModel productCategoryModel) {
        productCategoryRepository.save(productCategoryModel);
    }

    public ProductCategoryModel getProductCategory(Integer id) {
        return productCategoryRepository.findById(id).get();
    }

    public void deleteProductCategory(Integer id) {
        productCategoryRepository.deleteById(id);
    }
}
