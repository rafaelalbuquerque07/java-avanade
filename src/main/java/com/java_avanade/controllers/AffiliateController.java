package com.java_avanade.controllers;

import com.java_avanade.dtos.AffiliateDTO;
import com.java_avanade.services.AffiliateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affiliates")
@Tag(name = "Affiliate", description = "Affiliate management APIs")
public class AffiliateController {

    private final AffiliateService affiliateService;

    @Autowired
    public AffiliateController(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

    @GetMapping
    @Operation(summary = "Get all affiliates", description = "Retrieves a list of all affiliates")
    public ResponseEntity<List<AffiliateDTO>> getAllAffiliates() {
        return ResponseEntity.ok(affiliateService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get affiliate by ID", description = "Retrieves an affiliate by its ID")
    public ResponseEntity<AffiliateDTO> getAffiliateById(@PathVariable Long id) {
        return ResponseEntity.ok(affiliateService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create affiliate", description = "Creates a new affiliate")
    public ResponseEntity<AffiliateDTO> createAffiliate(@Valid @RequestBody AffiliateDTO affiliateDTO) {
        return new ResponseEntity<>(affiliateService.create(affiliateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update affiliate", description = "Updates an existing affiliate")
    public ResponseEntity<AffiliateDTO> updateAffiliate(@PathVariable Long id, @Valid @RequestBody AffiliateDTO affiliateDTO) {
        return ResponseEntity.ok(affiliateService.update(id, affiliateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete affiliate", description = "Deletes an affiliate")
    public ResponseEntity<Void> deleteAffiliate(@PathVariable Long id) {
        affiliateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}