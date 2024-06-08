package com.nvs.th_java.service;

import com.nvs.th_java.model.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategories();

    void updateCategory(Category category);

    Category getCategoryById(Long id);

    void deleteCategoryById(Long id);
}
