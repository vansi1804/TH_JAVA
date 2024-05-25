package com.nvs.th_java.controller;

import com.nvs.th_java.model.Student;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @GetMapping("/student")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form-student";
    }

    @PostMapping("/student")
    public String submitForm(@Valid Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "student/form-student";
        }
        model.addAttribute("message", "Student is added successfully");
        return "student/result-student";
    }
}
