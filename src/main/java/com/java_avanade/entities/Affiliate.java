package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um afiliado (fornecedor de fraldas ecológicas) no sistema.
 * Mapeada para a tabela "affiliates" no banco de dados.
 */
@Entity
@Table(name = "affiliates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Affiliate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do afiliado/fornecedor.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email de contato do afiliado/fornecedor.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Lista de produtos oferecidos por este afiliado.
     * Um afiliado pode oferecer vários produtos.
     */
    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}