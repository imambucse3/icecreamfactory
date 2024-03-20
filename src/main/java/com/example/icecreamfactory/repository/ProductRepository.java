package com.example.icecreamfactory.repository;

import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.util.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT e FROM Product e WHERE e.name LIKE %?1%")
    List<Product> findByNameContaining(String name);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.Id = :Id")
    List<Product> findByCategoriesId(Long Id);

    List<Product> findByProductType(ProductType productType);
}
