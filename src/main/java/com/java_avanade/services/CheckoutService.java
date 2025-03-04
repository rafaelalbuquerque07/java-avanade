package com.java_avanade.services;

import com.java_avanade.dtos.CartDTO;
import com.java_avanade.dtos.CheckoutDTO;
import com.java_avanade.entities.Checkout;
import com.java_avanade.entities.Order;
import com.java_avanade.entities.Product;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.CheckoutRepository;
import com.java_avanade.repositories.OrderRepository;
import com.java_avanade.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final StockService stockService;

    @Autowired
    public CheckoutService(CheckoutRepository checkoutRepository, OrderRepository orderRepository,
                           ProductRepository productRepository, CartService cartService, StockService stockService) {
        this.checkoutRepository = checkoutRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.stockService = stockService;
    }

    @Transactional(readOnly = true)
    public List<CheckoutDTO> findAll() {
        return checkoutRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CheckoutDTO findById(Long id) {
        Checkout checkout = checkoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkout not found with id: " + id));
        return convertToDTO(checkout);
    }

    @Transactional(readOnly = true)
    public List<CheckoutDTO> findByOrderId(Long orderId) {
        return checkoutRepository.findByOrderOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CheckoutDTO> processCheckout(Long orderId) {
        // Get order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Get all cart items for the order
        List<CartDTO> cartItems = cartService.findByOrderId(orderId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("No items in cart for order: " + orderId);
        }

        // Process each cart item
        List<Checkout> checkoutItems = cartItems.stream().map(cartItem -> {
            // Verify that the product exists
            Product product = productRepository.findById(cartItem.getProductCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + cartItem.getProductCode()));

            // Update the stock
            stockService.updateStockQuantity(cartItem.getProductCode(), cartItem.getQuantity());

            // Create checkout item
            Checkout checkout = new Checkout();
            checkout.setOrder(order);
            checkout.setProduct(product);
            checkout.setQuantity(cartItem.getQuantity());

            return checkout;
        }).collect(Collectors.toList());

        // Save all checkout items
        List<Checkout> savedCheckouts = checkoutRepository.saveAll(checkoutItems);

        // Clear the cart
        cartService.clearCart(orderId);

        return savedCheckouts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CheckoutDTO convertToDTO(Checkout checkout) {
        CheckoutDTO dto = new CheckoutDTO();
        dto.setId(checkout.getId());
        dto.setOrderId(checkout.getOrder() != null ? checkout.getOrder().getOrderId() : null);
        dto.setProductCode(checkout.getProduct() != null ? checkout.getProduct().getProductCode() : null);
        dto.setQuantity(checkout.getQuantity());
        return dto;
    }
}