package com.nvs.th_java.service;

import com.nvs.th_java.model.CartItem;
import com.nvs.th_java.model.Order;

import java.util.List;

public interface OrderService {
   Order createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String note, String paymentMethod, List<CartItem> cartItems) ;
}
