package com.java_avanade.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_avanade.dtos.ProductDTO;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setProductCode(101L);
        productDTO.setProductChoice("Premium");
        productDTO.setProductType("Infantil");
        productDTO.setAffiliateId(1L);
    }

    @Test
    @WithMockUser
    void getAllProducts_shouldReturnProducts() throws Exception {
        // Arrange
        when(productService.findAll()).thenReturn(Arrays.asList(productDTO));

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productCode", is(101)))
                .andExpect(jsonPath("$[0].productChoice", is("Premium")));

        verify(productService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void getProductById_withExistingId_shouldReturnProduct() throws Exception {
        // Arrange
        when(productService.findById(anyLong())).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(get("/api/products/101"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode", is(101)))
                .andExpect(jsonPath("$.productChoice", is("Premium")));

        verify(productService, times(1)).findById(101L);
    }

    @Test
    @WithMockUser
    void getProductById_withNonExistingId_shouldReturnNotFound() throws Exception {
        // Arrange
        when(productService.findById(anyLong())).thenThrow(new ResourceNotFoundException("Product not found"));

        // Act & Assert
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(999L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_withValidData_shouldReturnCreated() throws Exception {
        // Arrange
        when(productService.create(any(ProductDTO.class))).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode", is(101)))
                .andExpect(jsonPath("$.productChoice", is("Premium")));

        verify(productService, times(1)).create(any(ProductDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_withValidData_shouldReturnUpdatedProduct() throws Exception {
        // Arrange
        ProductDTO updatedProductDTO = productDTO;
        updatedProductDTO.setProductChoice("Standard");

        when(productService.update(anyLong(), any(ProductDTO.class))).thenReturn(updatedProductDTO);

        // Act & Assert
        mockMvc.perform(put("/api/products/101")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode", is(101)))
                .andExpect(jsonPath("$.productChoice", is("Standard")));

        verify(productService, times(1)).update(eq(101L), any(ProductDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_withExistingId_shouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(productService).delete(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/products/101")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(101L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void createProduct_withInsufficientRole_shouldReturnForbidden() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());

        verify(productService, never()).create(any(ProductDTO.class));
    }
}