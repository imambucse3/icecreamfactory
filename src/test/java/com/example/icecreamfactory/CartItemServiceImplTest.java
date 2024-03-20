package com.example.icecreamfactory;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.CartItemRepository;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.impl.CartItemServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Test
    public void testFindByUserAndProduct() {
        User user = new User();
        Product product = new Product();
        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(new CartItem());

        CartItem result = cartItemService.findByUserAndProduct(user, product);

        assertNotNull(result);
    }

    @Test
    public void testAddItemToCart() {
        Long productId = 1L;
        int quantity = 2;
        String email = "user@example.com";
        User user = new User();
        Product product = new Product();
        CartItem cartItem = new CartItem();
//        cartItem.setProduct(product);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(null);
        when(cartItemRepository.save(any())).thenReturn(cartItem);

        assertDoesNotThrow(() -> cartItemService.addItemToCart(productId, quantity, email));
    }

    @Test
    public void testGetCartItems() {
        String email = "user@example.com";
        User user = new User();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(cartItemRepository.findByUser(user)).thenReturn(cartItems);

        List<CartItem> result = cartItemService.getCartItems(email);

        assertEquals(cartItems.size(), result.size());
    }


    @Test
    public void testCalculateTotalPrice() {
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        CartItem cartItem2 = new CartItem();
        Product product1 = new Product();
        Product product2 = new Product();

        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(3);

        product1.setPrice(new BigDecimal("10.00"));
        product2.setPrice(new BigDecimal("15.00"));

        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        BigDecimal result = cartItemService.calculateTotalPrice(cartItems);

        assertEquals(new BigDecimal("65.00"), result);
    }


    @Test
    public void testIncreaseProductQuanity_NotFound() {
        Long cartItemId = 1L;
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cartItemService.increaseProductQuanity(cartItemId));
    }
}

