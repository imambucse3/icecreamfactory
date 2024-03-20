package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.dao.ProductDto;
import com.example.icecreamfactory.entity.CartItem;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.util.ProductType;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllSalesProduct() {

        List<Product> products =  productRepository.findByProductType(ProductType.SALES);
        return products.stream()
                .map((product) -> mapToProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllPurchaseProduct() {

        List<Product> products =  productRepository.findByProductType(ProductType.PURCHASE);
        return products.stream()
                .map((product) -> mapToProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsContainingName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Product saveProduct(Product product) {
        product.setProductType(ProductType.SALES);
        product.setCreatedDate(LocalDate.now());
        product.setUpdatedDate(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product _product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Not found Product with id = " + id));

        _product.setName(product.getName());
        _product.setPrice(product.getPrice());
        _product.setUpdatedDate(LocalDate.now());
        _product.setQuantity(product.getQuantity());
        if (product.getUnit() != null) {
            _product.setUnit(product.getUnit());
        }
        if (!product.getCategories().isEmpty() && product.getCategories() != null) {
            _product.setCategories(product.getCategories());
        }
        return productRepository.save(_product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByCategoriesId(Long categoryId) {
            return productRepository.findByCategoriesId(categoryId) ;
    }

    @Override
    public void decreaseProductQuantity(Long productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            int afterMinusProductQuantity = product.get().getQuantity() - quantity;
            if(afterMinusProductQuantity > 0) {
                product.get().setQuantity(afterMinusProductQuantity);
                productRepository.save(product.get());
            }
        }
    }

    @Override
    public void increaseProductQuantity(Long productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            int afteraddingProductQuantity = product.get().getQuantity() + quantity;
            if(afteraddingProductQuantity > 0) {
                product.get().setQuantity(afteraddingProductQuantity);
                productRepository.save(product.get());
            }
        }
    }

    @Override
    public ProductDto findByProduct(Long id) {
        return mapToProductDto(productRepository.findById(id).get());
    }

    @Override
    public Product savePurchaseProduct(Product product) {
        product.setProductType(ProductType.PURCHASE);
        product.setCreatedDate(LocalDate.now());
        product.setUpdatedDate(LocalDate.now());
        return productRepository.save(product);

    }

    @Override
    public Product updatePurchaseProduct(Product product, Long id) {
        return null;
    }

    private ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setUnit(product.getUnit() != null ? product.getUnit().toString() : "");
        productDto.setProductType(product.getProductType() != null ? product.getProductType().toString(): "");
        productDto.setCreatedDate(product.getCreatedDate() != null ? product.getCreatedDate().toString() : "");
        productDto.setUpdatedDated(product.getUpdatedDate() != null ? product.getUpdatedDate().toString() : "");
        productDto.setCategories(product.getCategories());
        return productDto;
    }
}
