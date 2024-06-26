package com.code.ecommerce.repository;


import com.code.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<User> findByZaloId(String zaloId);
    Boolean existsByZaloId(String zaloId);

    Page<User> findByEmailContainingIgnoreCase(String searchText, Pageable pageable);
}