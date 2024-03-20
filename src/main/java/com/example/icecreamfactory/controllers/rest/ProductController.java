package com.example.icecreamfactory.controllers.rest;

import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.service.CategoryService;
import com.example.icecreamfactory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/products")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        System.out.println("Save Product...");
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Saved");
    }

//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
//        List<Product> products = new ArrayList<>();
//        if (name == null) {
//            productService.getAllProducts().forEach(products::add);
//        } else {
//            productService.getProductsContainingName(name).forEach(products::add);
//        }
//        if (products.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getTutorialById(@PathVariable("id") Long id) {
        Optional<Product> product = Optional.ofNullable(productService.getProductById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Product not found")));

        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Product _product = productService.updateProduct(product, id);
        return new ResponseEntity<>(_product, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        // Logic to delete the employee with the given ID
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getAllProductsByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        if (! categoryService.getCategoryById(categoryId).isPresent()) {
            throw new ResponseNotFoundException("Not found Tutorial with id = " + categoryId);
        }

        List<Product> products = productService.getProductsByCategoriesId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
