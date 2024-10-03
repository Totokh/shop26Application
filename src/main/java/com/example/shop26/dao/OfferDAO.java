package com.example.shop26.dao;

public class OfferDAO {
    private Long ProductId;
    private String ProductName;
    private String ProductBrand;
    private String ProductSize;
    private Integer Quantity;
    private Float Price;

    public OfferDAO(Long ProductId, String ProductName, String ProductBrand, String ProductSize, Integer Quantity, Float Price) {
        this.ProductId = ProductId;
        this.ProductName = ProductName;
        this.ProductBrand = ProductBrand;
        this.ProductSize = ProductSize;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        this.ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String productBrand) {
        this.ProductBrand = productBrand;
    }

    public String getProductSize() {
        return ProductSize;
    }

    public void setProductSize(String productSize) {
        this.ProductSize = productSize;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        this.Quantity = quantity;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        this.Price = price;
    }
}
