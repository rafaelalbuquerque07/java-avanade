package com.java_avanade.repositories;

import com.java_avanade.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAffiliateId(Long affiliateId);
    List<Product> findByProductType(String productType);
}