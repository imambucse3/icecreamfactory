package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.CartItemRepository;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem findByUserAndProduct(User user, Product product) {
        return cartItemRepository.findByUserAndProduct(user, product);
    }

    @Override
    public void addItemToCart(Long productId, int quantity, String email) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void minusItemToCart(Long productId, int quantity, String email) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        int afterdecreaseQuantity = cartItem.getQuantity() - quantity;
        if (afterdecreaseQuantity > 0) {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
        }
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(String email) {
        User user = userRepository.findByEmail(email);
        return cartItemRepository.findByUser(user);
    }

    public void removeCartItem(String email, Long cartItemId) {
        User user = userRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (cartItem.getUser().equals(user)) {
            cartItemRepository.delete(cartItem);
        } else {
            throw new AccessDeniedException("You do not have permission to remove this cart item.");
        }
    }

    @Override
    public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemPrice = cartItem.getProduct().getPrice();
            int quantity = cartItem.getQuantity();
            totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        return totalPrice;
    }

    @Override
    public void clearCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        List<CartItem> cartItem = cartItemRepository.findByUser(user);
        if(!cartItem.isEmpty() && user != null) {
            cartItemRepository.deleteCartItemsByUser(user);
        }
    }

    @Override
    public void increaseProductQuanity(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        Product product = productRepository.findById(cartItem.getProduct().getId()).orElse(null);
        if(product != null) {
            int increasedQuantity = product.getQuantity() + cartItem.getQuantity();
            product.setQuantity(increasedQuantity);
            productRepository.save(product);
        }
    }
}
