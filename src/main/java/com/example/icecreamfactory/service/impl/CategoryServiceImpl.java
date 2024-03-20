package com.example.icecreamfactory.service.impl;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.repository.CategoryRepository;
import com.example.icecreamfactory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getCategoriessContainingName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Category _category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Not found Category with id = " + id));

        _category.setName(category.getName());
        _category.setDescription(category.getDescription());
        _category.setProducts(category.getProducts());
        return categoryRepository.save(_category);
    }

    @Override
    public List<Category> getCategoriesByProductId(Long productId) {
        return categoryRepository.findByProductsId(productId);
    }
}
