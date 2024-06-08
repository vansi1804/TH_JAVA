package com.nvs.th_java.service.impl;

import com.nvs.th_java.model.CartItem;
import com.nvs.th_java.model.Product;
import com.nvs.th_java.repository.ProductRepository;
import com.nvs.th_java.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@SessionScope
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final List<CartItem> cartItems = new ArrayList<>();
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with Id: " + productId));

        // check existing product in cart
        CartItem cartItem = cartItems.stream()
                .filter(pItem -> Objects.equals(pItem.getProduct().getId(), product.getId()))
                .findAny()
                .orElse(null);
        if (cartItem == null) {
            cartItems.add(new CartItem(product, product.getPrice(), quantity));
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
    }

    @Override
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    @Override
    public void clearCart() {
        cartItems.clear();
    }
}
