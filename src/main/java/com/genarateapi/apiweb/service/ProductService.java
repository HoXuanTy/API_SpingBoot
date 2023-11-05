package com.genarateapi.apiweb.service;

import com.genarateapi.apiweb.models.Product;
import com.genarateapi.apiweb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }



    @Override
    public Product updateProduct(Integer id, Product product) {
        return productRepository.findById(id)
                .map(newProduct ->{
                    newProduct.setProductName(product.getProductName());
                    newProduct.setImage(product.getImage());
                    newProduct.setPrice(product.getPrice());
                    return productRepository.save(newProduct);
                })
                .orElseGet(() -> {
                  product.setId(id);
                  return productRepository.save(product);
                });
    }

    @Override
    public boolean deleteProducr(Integer id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getOneProduct(Integer id) {
        return productRepository.findById(id);
    }
}
