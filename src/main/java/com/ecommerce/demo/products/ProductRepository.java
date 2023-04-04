package com.ecommerce.demo.products;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByIsDeleted(Pageable pageable, Boolean IsDeleted);

    Optional<Product> findById(Long id);

    @Modifying
    @Query("UPDATE Product p SET p.isDeleted = true WHERE p.id = ?1")
    void softDeleteProductById(Long id);
}
