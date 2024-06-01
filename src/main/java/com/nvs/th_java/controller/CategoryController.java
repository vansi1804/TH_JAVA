package com.nvs.th_java.controller;

import com.nvs.th_java.entity.Category;
import com.nvs.th_java.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/categories/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "/categories/add-category";
    }

    @PostMapping("/categories/add")
    public String addCategory(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/categories/add-category";
        }
        try {
            categoryService.addCategory(category);
        } catch (IllegalStateException e) {
            result.rejectValue("name", "error.category", e.getMessage());
            return "/categories/add-category";
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/categories/categories-list";
    }

    @GetMapping("/categories/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/categories/update-category";
    }

    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid Category category,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setId(id);
            return "/categories/update-category";
        }
        try {
            categoryService.updateCategory(category);
        } catch (IllegalStateException e) {
            result.rejectValue("name", "error.category", e.getMessage());
            return "/categories/update-category";
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        categoryService.deleteCategoryById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/categories";
    }

}
