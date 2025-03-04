package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um item finalizado no checkout.
 * Mapeada para a tabela "checkouts" no banco de dados.
 */
@Entity
@Table(name = "checkouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido ao qual este item de checkout pertence.
     * Um item de checkout está associado a um único pedido.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Produto que foi finalizado no checkout.
     * Um item de checkout representa um único produto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

    /**
     * Quantidade do produto confirmada no checkout.
     */
    @Column(nullable = false)
    private Integer quantity;
}