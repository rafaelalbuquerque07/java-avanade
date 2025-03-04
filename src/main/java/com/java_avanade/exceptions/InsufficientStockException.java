package com.java_avanade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exceção lançada quando não há estoque suficiente para atender a uma solicitação.
 * Esta exceção é usada durante o processo de adição ao carrinho ou finalização
 * de compra quando a quantidade solicitada excede o estoque disponível.
 * A anotação @ResponseStatus garante que quando esta exceção for lançada em um
 * controller, a resposta HTTP terá o status 400 (BAD_REQUEST).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message A mensagem detalhando o problema de estoque
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}