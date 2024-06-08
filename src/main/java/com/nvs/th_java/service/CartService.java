package com.nvs.th_java.service;

import com.nvs.th_java.model.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems();

    void addToCart(Long productId, int quantity);

    void removeFromCart(Long productId);

    void clearCart();
}
