package com.java_avanade.controllers;

import com.java_avanade.dtos.CartDTO;
import com.java_avanade.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Shopping cart management APIs")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Get all cart items", description = "Retrieves a list of all cart items")
    public ResponseEntity<List<CartDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cart item by ID", description = "Retrieves a cart item by its ID")
    public ResponseEntity<CartDTO> getCartItemById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get cart items by order", description = "Retrieves all cart items for a specific order")
    public ResponseEntity<List<CartDTO>> getCartItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(cartService.findByOrderId(orderId));
    }

    @PostMapping
    @Operation(summary = "Add to cart", description = "Adds a product to the shopping cart")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(cartService.addToCart(cartDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart item", description = "Updates an existing cart item")
    public ResponseEntity<CartDTO> updateCartItem(@PathVariable Long id, @Valid @RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.update(id, cartDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove from cart", description = "Removes an item from the shopping cart")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/order/{orderId}")
    @Operation(summary = "Clear cart", description = "Removes all items from the shopping cart for a specific order")
    public ResponseEntity<Void> clearCart(@PathVariable Long orderId) {
        cartService.clearCart(orderId);
        return ResponseEntity.noContent().build();
    }
}