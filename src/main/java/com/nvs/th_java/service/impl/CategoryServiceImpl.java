package com.nvs.th_java.service.impl;

import com.nvs.th_java.model.Category;
import com.nvs.th_java.repository.CategoryRepository;
import com.nvs.th_java.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category with ID " + id + " does not exist."));
    }

    public void addCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalStateException(category.getName() + " is existing");
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));

        if (categoryRepository.existsByIdNotAndNameIgnoreCase(category.getId(), category.getName())) {
            throw new IllegalStateException(category.getName() + " is existing");
        }
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
