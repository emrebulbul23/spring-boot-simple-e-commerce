package com.interviewtask.springbootsimpleecommerce.model;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    private String id;

    private String name;
    private String category;
    private Integer price;
    private String brand;
    private Integer stock;

    public Product() {
    }

    public Product(String id, String name, String category, Integer price, String brand, Integer stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stock=" + stock +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
