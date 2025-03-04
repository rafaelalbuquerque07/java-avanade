package com.java_avanade.services;

import com.java_avanade.dtos.StockDTO;
import com.java_avanade.entities.Product;
import com.java_avanade.entities.Stock;
import com.java_avanade.exceptions.InsufficientStockException;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.ProductRepository;
import com.java_avanade.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Autowired
    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        return stockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StockDTO findById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
        return convertToDTO(stock);
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findByProductCode(Long productCode) {
        return stockRepository.findByProductProductCode(productCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockDTO create(StockDTO stockDTO) {
        Product product = productRepository.findById(stockDTO.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + stockDTO.getProductCode()));

        Stock stock = convertToEntity(stockDTO);
        stock.setProduct(product);

        Stock savedStock = stockRepository.save(stock);
        return convertToDTO(savedStock);
    }

    @Transactional
    public StockDTO update(Long id, StockDTO stockDTO) {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));

        Product product = productRepository.findById(stockDTO.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + stockDTO.getProductCode()));

        existingStock.setProduct(product);
        existingStock.setQuantity(stockDTO.getQuantity());

        Stock updatedStock = stockRepository.save(existingStock);
        return convertToDTO(updatedStock);
    }

    @Transactional
    public void updateStockQuantity(Long productCode, Integer quantity) {
        List<Stock> stocks = stockRepository.findByProductProductCode(productCode);
        if (stocks.isEmpty()) {
            throw new ResourceNotFoundException("No stock found for product code: " + productCode);
        }

        // Find first stock with enough quantity
        Stock stockToUpdate = stocks.stream()
                .filter(s -> s.getQuantity() >= quantity)
                .findFirst()
                .orElseThrow(() -> new InsufficientStockException("Insufficient stock for product code: " + productCode));

        stockToUpdate.setQuantity(stockToUpdate.getQuantity() - quantity);
        stockRepository.save(stockToUpdate);
    }

    @Transactional(readOnly = true)
    public boolean checkStockAvailability(Long productCode, Integer quantity) {
        return stockRepository.findStockWithSufficientQuantity(productCode, quantity).isPresent();
    }

    @Transactional
    public void delete(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Stock not found with id: " + id);
        }
        stockRepository.deleteById(id);
    }

    private StockDTO convertToDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setProductCode(stock.getProduct() != null ? stock.getProduct().getProductCode() : null);
        dto.setQuantity(stock.getQuantity());
        return dto;
    }

    private Stock convertToEntity(StockDTO dto) {
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setQuantity(dto.getQuantity());
        return stock;
    }
}