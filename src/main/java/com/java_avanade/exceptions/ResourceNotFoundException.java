package com.java_avanade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 * Esta exceção é usada quando entidades como Affiliate, Product, Client, etc.
 * não são encontradas no banco de dados.
 * A anotação @ResponseStatus garante que quando esta exceção for lançada em um
 * controller, a resposta HTTP terá o status 404 (NOT_FOUND).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message A mensagem detalhando qual recurso não foi encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constrói uma nova exceção com a mensagem e causa especificadas.
     *
     * @param message A mensagem detalhando qual recurso não foi encontrado
     * @param cause A causa (exceção) que levou à não localização do recurso
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Método auxiliar para criar uma exceção formatada para uma entidade específica.
     *
     * @param resourceName Nome do recurso (ex: "Product", "Client")
     * @param fieldName Nome do campo usado para busca (ex: "id", "email")
     * @param fieldValue Valor do campo que não foi encontrado
     * @return Uma nova instância de ResourceNotFoundException com mensagem formatada
     */
    public static ResourceNotFoundException create(String resourceName, String fieldName, Object fieldValue) {
        return new ResourceNotFoundException(
                String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}