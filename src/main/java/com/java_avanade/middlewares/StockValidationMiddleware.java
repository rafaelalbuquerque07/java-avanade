package com.java_avanade.middlewares;

import com.java_avanade.dtos.CartDTO;
import com.java_avanade.exceptions.InsufficientStockException;
import com.java_avanade.services.StockService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Middleware para validação de estoque antes das operações de carrinho e checkout.
 * Utiliza AspectJ para interceptar chamadas aos métodos relevantes.
 */
@Aspect
@Component
public class StockValidationMiddleware {

    private final StockService stockService;

    @Autowired
    public StockValidationMiddleware(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Valida o estoque antes de adicionar um item ao carrinho
     */
    @Before("execution(* com.java_avanade.services.CartService.addToCart(..)) && args(cartDTO,..)")
    public void validateStockBeforeAddToCart(CartDTO cartDTO) {
        validateStockAvailability(cartDTO.getProductCode(), cartDTO.getQuantity());
    }

    /**
     * Valida o estoque antes de atualizar um item do carrinho
     */
    @Before(value = "execution(* com.java_avanade.services.CartService.update(..)) && args(*, cartDTO,..)", argNames = "cartDTO")
    public void validateStockBeforeUpdateCart(CartDTO cartDTO) {
        validateStockAvailability(cartDTO.getProductCode(), cartDTO.getQuantity());
    }

    /**
     * Valida o estoque antes do checkout
     */
    @Before("execution(* com.java_avanade.services.CheckoutService.processCheckout(..))")
    public void validateStockBeforeCheckout() {
        // A validação de estoque para cada item no carrinho é feita no CheckoutService
        // Este é um ponto de interceptação para possíveis validações adicionais no futuro
    }

    /**
     * Método comum para validar a disponibilidade de estoque
     */
    private void validateStockAvailability(Long productCode, Integer quantity) {
        if (!stockService.checkStockAvailability(productCode, quantity)) {
            throw new InsufficientStockException("Insufficient stock for product: " + productCode + ". Requested: " + quantity);
        }
    }
}