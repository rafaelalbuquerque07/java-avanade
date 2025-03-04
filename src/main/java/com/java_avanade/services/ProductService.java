package com.java_avanade.services;

import com.java_avanade.dtos.ProductDTO;
import com.java_avanade.entities.Affiliate;
import com.java_avanade.entities.Product;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.AffiliateRepository;
import com.java_avanade.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AffiliateRepository affiliateRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, AffiliateRepository affiliateRepository) {
        this.productRepository = productRepository;
        this.affiliateRepository = affiliateRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + id));
        return convertToDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findByAffiliateId(Long affiliateId) {
        return productRepository.findByAffiliateId(affiliateId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findByProductType(String productType) {
        return productRepository.findByProductType(productType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Affiliate affiliate = affiliateRepository.findById(productDTO.getAffiliateId())
                .orElseThrow(() -> new ResourceNotFoundException("Affiliate not found with id: " + productDTO.getAffiliateId()));

        Product product = convertToEntity(productDTO);
        product.setAffiliate(affiliate);

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + id));

        Affiliate affiliate = affiliateRepository.findById(productDTO.getAffiliateId())
                .orElseThrow(() -> new ResourceNotFoundException("Affiliate not found with id: " + productDTO.getAffiliateId()));

        existingProduct.setProductChoice(productDTO.getProductChoice());
        existingProduct.setProductType(productDTO.getProductType());
        existingProduct.setAffiliate(affiliate);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with code: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductCode(product.getProductCode());
        dto.setProductChoice(product.getProductChoice());
        dto.setProductType(product.getProductType());
        dto.setAffiliateId(product.getAffiliate() != null ? product.getAffiliate().getId() : null);
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setProductChoice(dto.getProductChoice());
        product.setProductType(dto.getProductType());
        return product;
    }
}