package com.simple_form.simple_form.controller;

import java.util.*;

import com.simple_form.simple_form.model.ProductCategoryModel;
import com.simple_form.simple_form.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/product-categories")
    public List<ProductCategoryModel> list() {
        return productCategoryService.listAll();
    }

    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryModel> get(@PathVariable Integer id) {
        try {
            ProductCategoryModel productCategoryModel = productCategoryService.getProductCategory(id);
            return new ResponseEntity<ProductCategoryModel>(productCategoryModel, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<ProductCategoryModel>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product-categories")
    public void addProductCategory(@RequestBody ProductCategoryModel productCategoryModel) {
        productCategoryService.save(productCategoryModel);
    }

    @PutMapping("/product-categories/{id}")
    public ResponseEntity<?> updateProductCategory(
        @RequestBody ProductCategoryModel productCategoryModel,
        @PathVariable Integer id) {
            try {
                ProductCategoryModel existingProductCategory = productCategoryService.getProductCategory(id);
                productCategoryService.save(existingProductCategory);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/product-categories/{id}")
    public void deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
    }
}
