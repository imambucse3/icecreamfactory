package com.example.icecreamfactory.service;

import com.example.icecreamfactory.dao.ProductDto;
import com.example.icecreamfactory.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllSalesProduct();

    List<ProductDto> getAllPurchaseProduct();

    Optional<Product> getProductById(Long id);
    List<Product> getProductsContainingName(String name);

    Product saveProduct(Product product);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);

    List<Product> getProductsByCategoriesId(Long categoryId);

    void decreaseProductQuantity(Long productId, int quantity);

    void increaseProductQuantity(Long productId, int quantity);

    ProductDto findByProduct(Long id);

    Product savePurchaseProduct(Product product);

    Product updatePurchaseProduct(Product product, Long id);
}
