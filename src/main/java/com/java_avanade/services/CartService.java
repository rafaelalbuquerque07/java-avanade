package com.java_avanade.services;

import com.java_avanade.dtos.CartDTO;
import com.java_avanade.entities.Cart;
import com.java_avanade.entities.Order;
import com.java_avanade.entities.Product;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.CartRepository;
import com.java_avanade.repositories.OrderRepository;
import com.java_avanade.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockService stockService;

    @Autowired
    public CartService(CartRepository cartRepository, OrderRepository orderRepository,
                       ProductRepository productRepository, StockService stockService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.stockService = stockService;
    }

    @Transactional(readOnly = true)
    public List<CartDTO> findAll() {
        return cartRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CartDTO findById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + id));
        return convertToDTO(cart);
    }

    @Transactional(readOnly = true)
    public List<CartDTO> findByOrderId(Long orderId) {
        return cartRepository.findByOrderOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartDTO addToCart(CartDTO cartDTO) {
        // Validate and retrieve entities
        Cart cart = new Cart();
        prepareCartData(cart, cartDTO);

        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    @Transactional
    public CartDTO update(Long id, CartDTO cartDTO) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + id));

        // Validate and update cart data
        prepareCartData(existingCart, cartDTO);

        Cart updatedCart = cartRepository.save(existingCart);
        return convertToDTO(updatedCart);
    }

    @Transactional
    public void removeFromCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart item not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clearCart(Long orderId) {
        cartRepository.deleteByOrderOrderId(orderId);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setOrderId(cart.getOrder() != null ? cart.getOrder().getOrderId() : null);
        dto.setProductCode(cart.getProduct() != null ? cart.getProduct().getProductCode() : null);
        dto.setPaymentType(cart.getPaymentType());
        dto.setQuantity(cart.getQuantity());
        return dto;
    }

    /**
     * Helper method to validate and prepare cart data
     * Extracts common logic from addToCart and update methods
     */
    private void prepareCartData(Cart cart, CartDTO cartDTO) {
        // Verify that the order exists
        Order order = orderRepository.findById(cartDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + cartDTO.getOrderId()));

        // Verify that the product exists
        Product product = productRepository.findById(cartDTO.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + cartDTO.getProductCode()));

        // Check if there is sufficient stock
        if (!stockService.checkStockAvailability(cartDTO.getProductCode(), cartDTO.getQuantity())) {
            throw new ResourceNotFoundException("Insufficient stock for product: " + cartDTO.getProductCode());
        }

        cart.setOrder(order);
        cart.setProduct(product);
        cart.setPaymentType(cartDTO.getPaymentType());
        cart.setQuantity(cartDTO.getQuantity());
    }
}