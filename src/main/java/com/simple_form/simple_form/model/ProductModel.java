package com.simple_form.simple_form.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "products_table")
public class ProductModel {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "product_name", nullable = false)
    String name;

    @Column(nullable = false)
    Long product_num;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    ProductCategoryModel productCategoryModel;

    @Column(name = "product_stock", nullable = false)
    Integer stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProduct_num() {
        return product_num;
    }

    public void setProduct_num(Long product_num) {
        this.product_num = product_num;
    }

    public ProductCategoryModel getProductCategoryModel() {
        return productCategoryModel;
    }

    public void setProductCategoryModel(ProductCategoryModel productCategoryModel) {
        this.productCategoryModel = productCategoryModel;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductModel [ id=" + id + 
                            ", name=" + name + 
                            ", product_num=" + product_num + 
                            ", stock=" + stock;
    }    
}
