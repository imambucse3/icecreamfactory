package com.example.icecreamfactory;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import com.example.icecreamfactory.repository.CategoryRepository;
import com.example.icecreamfactory.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categories.size(), result.size());
    }

    @Test
    public void testSaveCategory() {
        Category category = new Category();

        when(categoryRepository.save(any())).thenReturn(category);

        Category result = categoryService.saveCategory(category);

        assertNotNull(result);
        verify(categoryRepository).save(eq(category));
    }

    @Test
    public void testDelteCategory() {
        Long categoryId = 1L;

        categoryService.delteCategory(categoryId);

        verify(categoryRepository).deleteById(eq(categoryId));
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(categoryId);

        assertTrue(result.isPresent());
    }

    @Test
    public void testGetCategoriessContainingName() {
        String categoryName = "Chocolate";
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());

        when(categoryRepository.findByNameContaining(categoryName)).thenReturn(categories);

        List<Category> result = categoryService.getCategoriessContainingName(categoryName);

        assertEquals(categories.size(), result.size());
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setName("New Name");

        Category existingCategory = new Category();
        existingCategory.setName("Old Name");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any())).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(category, categoryId);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    public void testGetCategoriesByProductId() {
        Long productId = 1L;
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());

        when(categoryRepository.findByProductsId(productId)).thenReturn(categories);

        List<Category> result = categoryService.getCategoriesByProductId(productId);

        assertEquals(categories.size(), result.size());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResponseNotFoundException.class, () -> categoryService.updateCategory(new Category(), categoryId));
    }
}

