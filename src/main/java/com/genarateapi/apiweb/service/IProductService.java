package com.genarateapi.apiweb.service;

import com.genarateapi.apiweb.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    //add
    public Product addProduct(Product product);

    // Update
    public  Product updateProduct(Integer id, Product product);

    // Delete
    public  boolean deleteProducr(Integer id);

    // get all

    public List<Product> getAllProduct();

    //Get one product
    public Optional<Product> getOneProduct(Integer id);
}
