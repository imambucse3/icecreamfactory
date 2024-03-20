package com.example.icecreamfactory.repository;

import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
        CartItem findByUserAndProduct(User user, Product product);

        List<CartItem> findByUser(User user);

        void deleteCartItemsByUser(User user);
}
