package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Order;
import com.example.icecreamfactory.entity.OrderItem;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.OrderRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void placeOrder(String userEmail, List<CartItem> cartItems,
                           String shippingAddress, String paymentMethod, BigDecimal totalPrice) {
        User user = userRepository.findByEmail(userEmail);

        // Create a new Order entity
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDate.now());

        // Add the cart items to the order as OrderItems
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            order.addOrderItem(orderItem);
        }

        // Save the order in the database
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUsername(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
