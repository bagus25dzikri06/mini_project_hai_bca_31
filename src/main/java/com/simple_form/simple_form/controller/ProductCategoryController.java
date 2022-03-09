package com.simple_form.simple_form.controller;

import java.net.URI;
import java.util.Optional;

import com.simple_form.simple_form.model.ProductCategoryModel;
import com.simple_form.simple_form.repository.ProductCategoryRepository;
import com.simple_form.simple_form.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; 

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductCategoryController(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<ProductCategoryModel> create(@Validated @RequestBody ProductCategoryModel productCategoryModel) {
        ProductCategoryModel savedProductCategory = productCategoryRepository.save(productCategoryModel);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedProductCategory.getId()).toUri();

        return ResponseEntity.created(location).body(savedProductCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryModel> update(@PathVariable Integer id, @Validated @RequestBody ProductCategoryModel productCategoryModel) {
        Optional<ProductCategoryModel> optionalCategory = productCategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        productCategoryModel.setId(optionalCategory.get().getId());
        productCategoryRepository.save(productCategoryModel);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductCategoryModel> delete(@PathVariable Integer id) {
        Optional<ProductCategoryModel> optionalCategory = productCategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        productCategoryRepository.delete(optionalCategory.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryModel> getById(@PathVariable Integer id) {
        Optional<ProductCategoryModel> optionalCategory = productCategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalCategory.get());
    }

    @GetMapping
    public ResponseEntity<Page<ProductCategoryModel>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productCategoryRepository.findAll(pageable));
    }
}
