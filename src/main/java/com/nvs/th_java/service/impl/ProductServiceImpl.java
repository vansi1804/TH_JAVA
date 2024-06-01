package com.nvs.th_java.service.impl;

import com.nvs.th_java.entity.Product;
import com.nvs.th_java.repository.ProductRepository;
import com.nvs.th_java.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Product with ID: " +
                        id + " does not exist."));
    }
    public void addProduct(Product product) {
        if (productRepository.existsByNameIgnoreCase(product.getName())){
            throw new IllegalStateException(product.getName() + " is existing");
        }
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID: " +
                        product.getId() + " does not exist."));

        if (productRepository.existsByIdNotAndNameIgnoreCase(product.getId(), product.getName())){
            throw new IllegalStateException(product.getName() + " is existing");
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
}
