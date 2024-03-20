package com.example.icecreamfactory.repository;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT e FROM Category e WHERE e.name LIKE %?1%")
    List<Category> findByNameContaining(String name);

    @Query("SELECT p FROM Category p JOIN p.products c WHERE c.id = :Id")
    List<Category> findByProductsId(Long Id);
}
