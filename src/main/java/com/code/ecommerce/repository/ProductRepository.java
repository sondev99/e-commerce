package com.code.ecommerce.repository;


import com.code.ecommerce.entity.Brand;
import com.code.ecommerce.entity.Category;
import com.code.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable page);
    Page<Product> findByCategoryAndBrand(Category category, Brand brand, Pageable pageable);

    List<Product> findByCategory(Category categoryId);
}