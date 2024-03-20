package com.example.icecreamfactory.service;

import com.example.icecreamfactory.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Category saveCategory(Category category);

    void delteCategory(Long id);

    Optional<Category> getCategoryById(Long id);

    List<Category> getCategoriessContainingName(String name);

    Category updateCategory(Category category, Long id);

    List<Category> getCategoriesByProductId(Long productId);
}
