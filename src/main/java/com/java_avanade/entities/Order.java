package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pedido no sistema.
 * Mapeada para a tabela "orders" no banco de dados.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    /**
     * Cliente que realizou o pedido.
     * Um pedido pertence a um único cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Lista de itens no carrinho associados a este pedido.
     * Um pedido pode ter vários itens no carrinho.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems = new ArrayList<>();

    /**
     * Lista de itens de checkout associados a este pedido.
     * Um pedido pode ter vários itens de checkout (itens finalizados).
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Checkout> checkoutItems = new ArrayList<>();
}