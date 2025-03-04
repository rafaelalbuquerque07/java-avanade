package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um produto (fralda ecológica) no sistema.
 * Mapeada para a tabela "products" no banco de dados.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_code")
    private Long productCode;

    /**
     * Categoria do produto (ex: Premium, Standard).
     */
    @Column(nullable = false)
    private String productChoice;

    /**
     * Tipo de fralda (ex: Recém-nascido, Infantil).
     */
    @Column(nullable = false)
    private String productType;

    /**
     * Afiliado (fornecedor) que vende este produto.
     * Um produto é vendido por um único afiliado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id")
    private Affiliate affiliate;

    /**
     * Lista de registros de estoque para este produto.
     * Um produto pode ter vários registros de estoque.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();
}