package com.java_avanade.repositories;

import com.java_avanade.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByOrderOrderId(Long orderId);
    void deleteByOrderOrderId(Long orderId);
}