package com.java_avanade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exceção lançada quando um cliente não é encontrado.
 * Esta exceção é uma especialização de ResourceNotFoundException específica para a entidade Client.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message A mensagem detalhando qual cliente não foi encontrado
     */
    public ClientNotFoundException(String message) {
        super(message);
    }
}