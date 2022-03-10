package com.simple_form.simple_form.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.simple_form.simple_form.exception.ResourceNotFoundException;
import com.simple_form.simple_form.model.ProductCategoryModel;
import com.simple_form.simple_form.model.ProductModel;
import com.simple_form.simple_form.repository.ProductCategoryRepository;
import com.simple_form.simple_form.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
//import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @PostMapping
    public ResponseEntity<ProductModel> create(@RequestBody @Validated ProductModel productModel) {
        Optional<ProductCategoryModel> optionalCategory = productCategoryRepository.findById(productModel.getProductCategoryModel().getId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        productModel.setProductCategoryModel(optionalCategory.get());

        ProductModel savedProduct = productRepository.save(productModel);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductModel> update(
        @RequestBody @Validated ProductModel productModel,
        @PathVariable Integer id
    ) {
        ProductModel _productModel = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Not found Product with id = " + id)
        );

        _productModel.setName(productModel.getName());
        _productModel.setProduct_num(productModel.getProduct_num());
        _productModel.setStock(productModel.getStock());

        return new ResponseEntity<>(productRepository.save(_productModel), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductModel> delete(@PathVariable Integer id) {
        Optional<ProductModel> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        productRepository.delete(optionalProduct.get());

        return ResponseEntity.noContent().build();
    }

    /*@GetMapping
    public ResponseEntity<Page<ProductModel>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }*/

    @GetMapping
    public String getProductPage(Model model) {
        List<ProductModel> products = productRepository.findAll();
        model.addAttribute("getProductPage", products);

        return "product_page";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getById(@PathVariable Integer id) {
        Optional<ProductModel> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalProduct.get());
    }
}
