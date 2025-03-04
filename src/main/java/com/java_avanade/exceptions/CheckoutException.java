package com.java_avanade.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exceção lançada quando ocorre um erro durante o processo de checkout.
 * Esta exceção é usada para problemas específicos no fluxo de finalização de compra.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class CheckoutException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long orderId;
    private final String errorCode;

    /**
     * Constrói uma nova exceção com a mensagem, ID do pedido e código de erro.
     *
     * @param message A mensagem detalhando o problema ocorrido
     * @param orderId O ID do pedido onde ocorreu o problema
     * @param errorCode Um código de erro específico para categorização
     */
    public CheckoutException(String message, Long orderId, String errorCode) {
        super(message);
        this.orderId = orderId;
        this.errorCode = errorCode;
    }
}