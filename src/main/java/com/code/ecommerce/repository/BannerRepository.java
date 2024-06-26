package com.code.ecommerce.repository;

import com.code.ecommerce.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, String> {
    Page<Banner> findByNameContainingIgnoreCase (String name, Pageable page);
}