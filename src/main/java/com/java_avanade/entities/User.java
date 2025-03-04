package com.java_avanade.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um usuário do sistema.
 * Utilizada para autenticação e autorização.
 * Mapeada para a tabela "users" no banco de dados.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome de usuário para login.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Email do usuário.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Senha do usuário (armazenada com criptografia).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Papéis/roles do usuário (ex: "ADMIN", "USER").
     * Utilizados para controle de acesso baseado em papel.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    /**
     * Construtor com parâmetros (exceto ID, que é gerado automaticamente).
     */
    public User(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
