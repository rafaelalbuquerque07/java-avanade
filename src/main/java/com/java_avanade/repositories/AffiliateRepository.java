package com.java_avanade.repositories;

import com.java_avanade.entities.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {
    boolean existsByEmail(String email);
}