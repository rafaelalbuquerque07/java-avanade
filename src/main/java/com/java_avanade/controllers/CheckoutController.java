package com.java_avanade.controllers;

import com.java_avanade.dtos.CheckoutDTO;
import com.java_avanade.services.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkouts")
@Tag(name = "Checkout", description = "Checkout process APIs")
@SecurityRequirement(name = "bearerAuth")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping
    @Operation(summary = "Get all checkouts", description = "Retrieves a list of all checkout records")
    public ResponseEntity<List<CheckoutDTO>> getAllCheckouts() {
        return ResponseEntity.ok(checkoutService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get checkout by ID", description = "Retrieves a checkout record by its ID")
    public ResponseEntity<CheckoutDTO> getCheckoutById(@PathVariable Long id) {
        return ResponseEntity.ok(checkoutService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get checkouts by order", description = "Retrieves all checkout records for a specific order")
    public ResponseEntity<List<CheckoutDTO>> getCheckoutsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(checkoutService.findByOrderId(orderId));
    }

    @PostMapping("/process/{orderId}")
    @Operation(summary = "Process checkout", description = "Processes the checkout for a specific order")
    public ResponseEntity<List<CheckoutDTO>> processCheckout(@PathVariable Long orderId) {
        return new ResponseEntity<>(checkoutService.processCheckout(orderId), HttpStatus.CREATED);
    }
}