package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um item no carrinho de compras.
 * Mapeada para a tabela "carts" no banco de dados.
 */
@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido ao qual este item do carrinho pertence.
     * Um item do carrinho está associado a um único pedido.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Produto adicionado ao carrinho.
     * Um item do carrinho representa um único produto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

    /**
     * Tipo de pagamento selecionado para este item.
     */
    @Column(nullable = false)
    private String paymentType;

    /**
     * Quantidade do produto selecionada.
     */
    @Column(nullable = false)
    private Integer quantity;
}