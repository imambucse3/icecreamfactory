package com.example.icecreamfactory.controllers.rest;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.service.CategoryService;
import com.example.icecreamfactory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping("/categories")
    public ResponseEntity<String> saveCategory(@RequestBody Category category) {
        System.out.println("Save Category...");
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category Saved");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> listOfCategories = categoryService.getAllCategories();
        if(listOfCategories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listOfCategories);

    }

    @GetMapping("/products/{productId}/categories")
    public ResponseEntity<List<Category>> getAllCategoriessByProductId(@PathVariable(value = "productId") Long productId) {
        if (!productService.getProductById(productId).isPresent()) {
            throw new ResponseNotFoundException("Not found Product with id = " + productId);
        }

        List<Category> categories = categoryService.getCategoriesByProductId(productId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long id) {
        // Logic to delete the category with the given ID
        System.out.println("delete ID" +id);
        categoryService.delteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        Category _category = categoryService.updateCategory(category, id);
        return new ResponseEntity<>(_category, HttpStatus.OK);
    }
}
