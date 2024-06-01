package com.nvs.th_java.service;

import com.nvs.th_java.entity.Product;

import java.util.List;


public interface ProductService {
    List<Product> getAllProducts();

    void saveProduct(Product product);
}
