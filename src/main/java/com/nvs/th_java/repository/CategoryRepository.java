package com.nvs.th_java.repository;

import com.nvs.th_java.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByIdNotAndNameIgnoreCase(Long id, String name);

    boolean existsByNameIgnoreCase(String name);
}
