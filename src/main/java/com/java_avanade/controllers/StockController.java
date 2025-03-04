package com.java_avanade.controllers;

import com.java_avanade.dtos.StockDTO;
import com.java_avanade.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock", description = "Stock management APIs")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    @Operation(summary = "Get all stocks", description = "Retrieves a list of all stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID", description = "Retrieves a stock by its ID")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.findById(id));
    }

    @GetMapping("/product/{productCode}")
    @Operation(summary = "Get stocks by product", description = "Retrieves all stocks for a specific product")
    public ResponseEntity<List<StockDTO>> getStocksByProductCode(@PathVariable Long productCode) {
        return ResponseEntity.ok(stockService.findByProductCode(productCode));
    }

    @GetMapping("/availability")
    @Operation(summary = "Check stock availability", description = "Checks if there is sufficient stock for a product")
    public ResponseEntity<Boolean> checkStockAvailability(
            @RequestParam Long productCode,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(stockService.checkStockAvailability(productCode, quantity));
    }

    @PostMapping
    @Operation(summary = "Create stock", description = "Creates a new stock entry")
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        return new ResponseEntity<>(stockService.create(stockDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update stock", description = "Updates an existing stock entry")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.update(id, stockDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete stock", description = "Deletes a stock entry")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}