package com.nvs.th_java.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CartItem {
    @NotNull(message = "Product is required")
    private Product product;

    private double price;

    private int quantity;
}
