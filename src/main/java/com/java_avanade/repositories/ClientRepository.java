package com.java_avanade.repositories;

import com.java_avanade.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de acesso a dados da entidade Client.
 * Fornece métodos para busca, criação, atualização e remoção de clientes.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Verifica se existe um cliente com o email especificado.
     *
     * @param email O email a ser verificado
     * @return true se existir um cliente com este email, false caso contrário
     */
    boolean existsByEmail(String email);
}