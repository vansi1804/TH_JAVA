package com.nvs.th_java.controller;

import com.nvs.th_java.model.CartItem;
import com.nvs.th_java.service.CartService;
import com.nvs.th_java.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/checkout")
    public String checkout() {
        return "/carts/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestParam("customerName") String customerName,
                              @RequestParam("shippingAddress") String shippingAddress,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("email") String email,
                              @RequestParam("notes") String notes,
                              @RequestParam("paymentMethod") String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        orderService.createOrder(customerName, shippingAddress, phoneNumber, email, notes, paymentMethod, cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "carts/order-confirmation";
    }
}
