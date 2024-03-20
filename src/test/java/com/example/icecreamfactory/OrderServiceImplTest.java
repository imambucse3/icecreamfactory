package com.example.icecreamfactory;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Order;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.OrderRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testPlaceOrder() {
        String userEmail = "user@example.com";
        String shippingAddress = "123 Main St";
        String paymentMethod = "Credit Card";
        BigDecimal totalPrice = new BigDecimal("50.00");
        User user = new User();
        Product product = new Product();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        when(orderRepository.save(any())).thenReturn(new Order());
    }

    @Test
    public void testGetOrdersByUsername() {
        String userEmail = "user@example.com";
        User user = new User();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByUsername(userEmail);

        assertEquals(orders.size(), result.size());
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders.size(), result.size());
    }
}
