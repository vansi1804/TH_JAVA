package com.nvs.th_java.service.impl;

import com.nvs.th_java.model.CartItem;
import com.nvs.th_java.model.Order;
import com.nvs.th_java.model.OrderDetail;
import com.nvs.th_java.repository.OrderDetailsRepository;
import com.nvs.th_java.repository.OrderRepository;
import com.nvs.th_java.service.CartService;
import com.nvs.th_java.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartService cartService;

    @Override
    public Order createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String note, String paymentMethod, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);
        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            orderDetailsRepository.save(detail);
        }
        cartService.clearCart();
        return order;
    }

}
