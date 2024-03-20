package com.example.icecreamfactory.service;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {
    CartItem findByUserAndProduct(User user, Product product);
    void addItemToCart(Long productId, int quantity, String email);

    void minusItemToCart(Long productId, int quantity, String email);

    List<CartItem> getCartItems(String username);

    void removeCartItem(String username, Long cartItemId);

    BigDecimal calculateTotalPrice(List<CartItem> cartItems);

    void clearCart(String username);

    void increaseProductQuanity(Long cartItemId);
}
