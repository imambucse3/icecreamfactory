//package com.example.icecreamfactory.service.impl;
//
//import com.example.icecreamfactory.dao.OrderRequest;
//import com.example.icecreamfactory.entity.Cart;
//import com.example.icecreamfactory.entity.CartItem;
//import com.example.icecreamfactory.entity.Order;
//import com.example.icecreamfactory.entity.OrderItem;
//import com.example.icecreamfactory.entity.util.OrderStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class OrderService {
//    private final List<Order> orders = new ArrayList<>();
//    private final CartService cartService;
//
//    public OrderService(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    public Long placeOrder(OrderRequest orderRequest) {
//        Cart cart = cartService.getCart();
//
//        Order order = new Order();
//        order.setStatus(OrderStatus.PLACED);
//        order.setTotalPrice(cart.getTotalPrice());
//        order.setShippingAddress(orderRequest.getShippingAddress());
//        order.setPaymentMethod(orderRequest.getPaymentMethod());
//
//        for (CartItem cartItem : cart.getCartItems()) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(cartItem.getProduct());
//            orderItem.setQuantity(cartItem.getQuantity());
//            order.addOrderItem(orderItem);
//        }
//
//        orders.add(order);
//        cart.getCartItems().clear();
//
//        return order.getId();
//    }
//
//    public void confirmOrder(Long orderId) {
//        Order order = getOrderById(orderId);
//        order.setStatus(OrderStatus.CONFIRMED);
//    }
//
//    public Order getOrderById(Long orderId) {
//        return orders.stream()
//                .filter(order -> order.getId().equals(orderId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
//    }
//}
