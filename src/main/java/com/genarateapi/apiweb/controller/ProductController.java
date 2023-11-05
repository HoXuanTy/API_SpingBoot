package com.genarateapi.apiweb.controller;

import com.genarateapi.apiweb.models.Product;
import com.genarateapi.apiweb.models.ResposeObject;
import com.genarateapi.apiweb.repositories.ProductRepository;
import com.genarateapi.apiweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/product")
@CrossOrigin(origins ="http://localhost:3000")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    // this request is: http://localhost:8080/api/v1/product
    @GetMapping
    List<Product> getAllProducts(){
        return iProductService.getAllProduct();
    }

    //Get detail product
    @GetMapping("/{id}")
    // Let's return an object with: data, message, status
    ResponseEntity<ResposeObject> finbyId(@PathVariable Integer id){
        Optional<Product> foundProduct = iProductService.getOneProduct(id);
        if(foundProduct.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResposeObject("ok","Query successfully", foundProduct)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResposeObject("Error","Cannot find product with id = "+id, "")
            );
        }

    }

//    //insert new Product with POST method
    @PostMapping("/insert")
    ResponseEntity<ResposeObject> insertProduct(@RequestBody Product newProduct){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResposeObject("ok","Insert product successfully", iProductService.addProduct(newProduct))
        );
    }

   //update product
    @PutMapping("/{id}")
    ResponseEntity<ResposeObject> updateProduct(@RequestBody Product newProduct, @PathVariable Integer id){
        Product updatedProduct = iProductService.updateProduct(id,newProduct);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResposeObject("ok","Update product successfully", newProduct)
        );
    }

    // delete product
    @DeleteMapping("/{id}")
    ResponseEntity<ResposeObject> deleteProduct(@PathVariable Integer id){
        Optional<Product> exists = iProductService.getOneProduct(id);
        if(exists.isPresent()){
            iProductService.deleteProducr(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResposeObject("ok","Delete product successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResposeObject("Error","cannot find product to delete ", "")
        );
    };

}
