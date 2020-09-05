package com.interviewtask.springbootsimpleecommerce.model;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    private String id;

    private String name;
    private String category;
    private Integer price;
    private String brand;
    private String seller;
    private String size;

    /*
     * Private constructor to hide implicit public one.
     */
    private Product() {
    }

    /**
     * Constructor.
     *
     * @param name name
     * @param category category
     * @param price price
     * @param brand brand
     * @param seller seller
     * @param size size
     */
    public Product(String name, String category, Integer price, String brand, String seller, String size) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.seller = seller;
        this.size = size;
    }

    /**
     * Pretty print product object.
     * @return String
     */
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", seller='" + seller + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    /**
     * Get id.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get category.
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set category.
     * @param category category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get price.
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Set price.
     * @param price price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Get brand.
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set brand.
     * @param brand brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get seller
     * @return seller
     */
    public String getSeller() {
        return seller;
    }

    /**
     * Set seller.
     * @param seller seller
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * Get size.
     * @return size
     */
    public String getSize() {
        return size;
    }

    /**
     * Set size.
     * @param size size
     */
    public void setSize(String size) {
        this.size = size;
    }
}
