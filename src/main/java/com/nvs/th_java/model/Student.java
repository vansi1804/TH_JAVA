package com.nvs.th_java.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Student {
    @NotBlank(message = "Please enter name")
    private String name;

    @Min(value = 18, message = "age >= 18")
    @Max(value = 100, message = "age <= 100")
    private int age;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Faculty must be letter")
    private String faculty;
}
