package com.java_avanade.repositories;

import com.java_avanade.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByProductProductCode(Long productCode);

    @Query("SELECT s FROM Stock s WHERE s.product.productCode = :productCode AND s.quantity >= :quantity")
    Optional<Stock> findStockWithSufficientQuantity(@Param("productCode") Long productCode, @Param("quantity") Integer quantity);
}