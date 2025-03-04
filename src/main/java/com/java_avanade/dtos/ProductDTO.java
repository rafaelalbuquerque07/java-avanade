package com.java_avanade.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productCode;

    @NotBlank(message = "Product choice is required")
    private String productChoice;

    @NotBlank(message = "Product type is required")
    private String productType;

    @NotNull(message = "Affiliate ID is required")
    private Long affiliateId;
}