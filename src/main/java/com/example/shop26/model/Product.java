package com.example.shop26.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private Long productId;

    @Column
    private String productName;

    @Column
    private String productBrand;

    @Column()
    private String productSize;

    public Product() {
    }

    public Product(Long productId, String productName, String productBrand, String productSize) {
        this.productId = productId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productSize = productSize;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }
    // getters and setters
}