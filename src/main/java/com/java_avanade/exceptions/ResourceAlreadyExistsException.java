package com.java_avanade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exceção lançada quando se tenta criar um recurso que já existe.
 * Esta exceção é usada em casos como tentativa de cadastro de email duplicado,
 * nome de usuário já existente, etc.
 * A anotação @ResponseStatus garante que quando esta exceção for lançada em um
 * controller, a resposta HTTP terá o status 409 (CONFLICT).
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message A mensagem detalhando qual recurso já existe
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Método auxiliar para criar uma exceção formatada para uma entidade específica.
     *
     * @param resourceName Nome do recurso (ex: "Client", "Affiliate")
     * @param fieldName Nome do campo que já existe (ex: "email", "username")
     * @param fieldValue Valor do campo duplicado
     * @return Uma nova instância de ResourceAlreadyExistsException com mensagem formatada
     */
    public static ResourceAlreadyExistsException create(String resourceName, String fieldName, Object fieldValue) {
        return new ResourceAlreadyExistsException(
                String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}