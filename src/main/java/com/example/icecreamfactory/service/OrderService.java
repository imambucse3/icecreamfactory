package com.example.icecreamfactory.service;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void placeOrder(String username, List<CartItem> cartItems,
                    String shippingAddress, String paymentMethod, BigDecimal totalPrice);

    List<Order> getOrdersByUsername(String userEmail);

    List<Order> getAllOrders();
}
