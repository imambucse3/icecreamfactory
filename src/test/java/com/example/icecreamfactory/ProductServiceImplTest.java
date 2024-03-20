package com.example.icecreamfactory;

import com.example.icecreamfactory.dao.ProductDto;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.util.ProductType;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testGetAllSalesProduct() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(productRepository.findByProductType(ProductType.SALES)).thenReturn(products);

        List<ProductDto> result = productService.getAllSalesProduct();

        assertEquals(products.size(), result.size());
    }

    @Test
    public void testGetAllPurchaseProduct() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(productRepository.findByProductType(ProductType.PURCHASE)).thenReturn(products);

        List<ProductDto> result = productService.getAllPurchaseProduct();

        assertEquals(products.size(), result.size());
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
    }

    @Test
    public void testGetProductsContainingName() {
        String productName = "Chocolate";
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(productRepository.findByNameContaining(productName)).thenReturn(products);

        List<Product> result = productService.getProductsContainingName(productName);

        assertEquals(products.size(), result.size());
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();

        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertNotNull(result);
        assertEquals(ProductType.SALES, result.getProductType());
        assertNotNull(result.getCreatedDate());
        assertNotNull(result.getUpdatedDate());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setName("New Name");

        Product existingProduct = new Product();
        existingProduct.setName("Old Name");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(existingProduct);

        Product result = productService.updateProduct(product, productId);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(eq(productId));
    }

    @Test
    public void testDecreaseProductQuantity() {
        Long productId = 1L;
        int quantity = 10;
        Product product = new Product();
        product.setQuantity(20);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        productService.decreaseProductQuantity(productId, quantity);

        verify(productRepository).save(eq(product));
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testIncreaseProductQuantity() {
        Long productId = 1L;
        int quantity = 5;
        Product product = new Product();
        product.setQuantity(20);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        productService.increaseProductQuantity(productId, quantity);

        verify(productRepository).save(eq(product));
        assertEquals(25, product.getQuantity());
    }

    @Test
    public void testFindByProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDto result = productService.findByProduct(productId);

        assertNotNull(result);
    }

    @Test
    public void testSavePurchaseProduct() {
        Product product = new Product();

        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.savePurchaseProduct(product);

        assertNotNull(result);
        assertEquals(ProductType.PURCHASE, result.getProductType());
        assertNotNull(result.getCreatedDate());
        assertNotNull(result.getUpdatedDate());
    }

    @Test
    public void testUpdatePurchaseProduct() {
        // Implement this test based on your requirements
    }

    @Test
    public void testUpdateProduct_NotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResponseNotFoundException.class, () -> productService.updateProduct(new Product(), productId));
    }
}

