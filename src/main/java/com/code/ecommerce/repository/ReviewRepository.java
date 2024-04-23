package com.code.ecommerce.repository;

import com.code.ecommerce.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {

    Page<Review> findByProductId(Pageable pageable, String productId);

    Page<Review> findByProductIdAndUserId(Pageable pageable, String productId, String userId);

}
