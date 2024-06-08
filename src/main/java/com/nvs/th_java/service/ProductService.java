package com.nvs.th_java.service;

import com.nvs.th_java.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void addProduct(Product product);

    Product getProductById(Long id);

    void updateProduct(Product product);

    void deleteProductById(Long id);
}
