package com.genarateapi.apiweb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tblproduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //auto-increment
    private Integer id;
    @Column(nullable = false, unique = true, length = 300)
    private  String productName;
    private String image;
    private Double price;

    public  Product(){}

    public Product(String productName, String image, Double price) {
        this.productName = productName;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }

}
