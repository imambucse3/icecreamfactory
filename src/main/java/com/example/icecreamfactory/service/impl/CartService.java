package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.entity.Cart;
import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.service.CartItemService;
import com.example.icecreamfactory.service.ProductService;
import com.example.icecreamfactory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;
    private final Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addItemToCart(Long productId, int quantity, String email) {
        User user = userService.findUserByEmail(email);
        Product product = productService.getProductById(productId).orElse(null);
        if (product != null) {
            CartItem cartItem = new CartItem(product, quantity);
            cart.addCartItem(cartItem);
        }
    }

    public Cart getCart() {
        return cart;
    }
}

