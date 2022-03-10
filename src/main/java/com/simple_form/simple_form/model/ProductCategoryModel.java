package com.simple_form.simple_form.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "product_categories_table")
public class ProductCategoryModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Integer id;

    @Column(name = "product_category", nullable = false, length = 100)
    String category;

    @Column(name = "product_desc", nullable = false)
    String description;

    @OneToMany(mappedBy = "productCategoryModel", cascade = CascadeType.ALL)
    Set<ProductModel> products = new HashSet<>();

    public ProductCategoryModel() {

    }

    public ProductCategoryModel(Integer id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductModel> products) {
        this.products = products;

        for (ProductModel p : products) {
            p.setProductCategoryModel(this);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryModel that = (ProductCategoryModel) o;
        return Objects.equals(id, that.id) && 
                Objects.equals(category, that.category) && 
                Objects.equals(description, that.description);
    }

    @Override
    public String toString() {
        return "ProductCategoryModel" + 
                " [category=" + category + 
                ", description=" + description + 
                ", id=" + id + "]";
    } 
    
    
}
