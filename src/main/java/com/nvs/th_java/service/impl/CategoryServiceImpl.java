package com.nvs.th_java.service.impl;

import com.nvs.th_java.entity.Category;
import com.nvs.th_java.repository.CategoryRepository;
import com.nvs.th_java.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Retrieve all categories from the database.
     *
     * @return a list of categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieve a category by its id.
     *
     * @param id the id of the category to retrieve
     * @return an Optional containing the found category or empty if not found
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category with ID " + id + " does not exist."));
    }

    /**
     * Add a new category to the database.
     *
     * @param category the category to add
     */
    public void addCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new IllegalStateException("Existing category with Name " + category.getName());
        }
        categoryRepository.save(category);
    }

    /**
     * Update an existing category.
     *
     * @param category the category with updated information
     */
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));

        if (categoryRepository.existsByIdNotAndNameIgnoreCase(category.getId(), category.getName())){
            throw new IllegalStateException("Existing category with Name " + category.getName());
        }
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    /**
     * Delete a category by its id.
     *
     * @param id the id of the category to delete
     */
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }
}
