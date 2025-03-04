package com.java_avanade.services;

import com.java_avanade.dtos.ProductDTO;
import com.java_avanade.entities.Affiliate;
import com.java_avanade.entities.Product;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.AffiliateRepository;
import com.java_avanade.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AffiliateRepository affiliateRepository;

    @InjectMocks
    private ProductService productService;

    private Affiliate affiliate;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Setup do afiliado
        affiliate = new Affiliate();
        affiliate.setId(1L);
        affiliate.setName("Test Affiliate");
        affiliate.setEmail("affiliate@test.com");

        // Setup do produto
        product = new Product();
        product.setProductCode(101L);
        product.setProductChoice("Premium");
        product.setProductType("Infantil");
        product.setAffiliate(affiliate);

        // Setup do DTO
        productDTO = new ProductDTO();
        productDTO.setProductCode(101L);
        productDTO.setProductChoice("Premium");
        productDTO.setProductType("Infantil");
        productDTO.setAffiliateId(1L);
    }

    @Test
    void findAll_shouldReturnListOfProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getProductCode(), result.get(0).getProductCode());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findById_withExistingId_shouldReturnProduct() {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = productService.findById(101L);

        // Assert
        assertNotNull(result);
        assertEquals(product.getProductCode(), result.getProductCode());
        verify(productRepository, times(1)).findById(101L);
    }

    @Test
    void findById_withNonExistingId_shouldThrowException() {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(999L);
        });
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void create_shouldSaveAndReturnProduct() {
        // Arrange
        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.of(affiliate));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ProductDTO result = productService.create(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productDTO.getProductCode(), result.getProductCode());
        verify(affiliateRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void create_withNonExistingAffiliate_shouldThrowException() {
        // Arrange
        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.create(productDTO);
        });
        verify(affiliateRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void update_withExistingIdAndAffiliate_shouldUpdateAndReturnProduct() {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.of(affiliate));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productDTO.setProductChoice("Standard"); // Modificando o DTO

        // Act
        ProductDTO result = productService.update(101L, productDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Standard", result.getProductChoice());
        verify(productRepository, times(1)).findById(101L);
        verify(affiliateRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_withExistingId_shouldDeleteProduct() {
        // Arrange
        when(productRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyLong());

        // Act
        productService.delete(101L);

        // Assert
        verify(productRepository, times(1)).existsById(101L);
        verify(productRepository, times(1)).deleteById(101L);
    }

    @Test
    void delete_withNonExistingId_shouldThrowException() {
        // Arrange
        when(productRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(999L);
        });
        verify(productRepository, times(1)).existsById(999L);
        verify(productRepository, never()).deleteById(anyLong());
    }
}