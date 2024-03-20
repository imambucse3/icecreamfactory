package com.example.icecreamfactory.entity.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutForm {
    private String shippingAddress;
    private String paymentMethod;
    private BigDecimal totalPrice;

    // Getters and setters
}

