package com.java_avanade.services;

import com.java_avanade.dtos.AffiliateDTO;
import com.java_avanade.entities.Affiliate;
import com.java_avanade.exceptions.ResourceAlreadyExistsException;
import com.java_avanade.exceptions.ResourceNotFoundException;
import com.java_avanade.repositories.AffiliateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffiliateService {

    private final AffiliateRepository affiliateRepository;

    @Autowired
    public AffiliateService(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Transactional(readOnly = true)
    public List<AffiliateDTO> findAll() {
        return affiliateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AffiliateDTO findById(Long id) {
        Affiliate affiliate = affiliateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affiliate not found with id: " + id));
        return convertToDTO(affiliate);
    }

    @Transactional
    public AffiliateDTO create(AffiliateDTO affiliateDTO) {
        if (affiliateRepository.existsByEmail(affiliateDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Affiliate already exists with email: " + affiliateDTO.getEmail());
        }

        Affiliate affiliate = convertToEntity(affiliateDTO);
        Affiliate savedAffiliate = affiliateRepository.save(affiliate);
        return convertToDTO(savedAffiliate);
    }

    @Transactional
    public AffiliateDTO update(Long id, AffiliateDTO affiliateDTO) {
        Affiliate existingAffiliate = affiliateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affiliate not found with id: " + id));

        // Check if email is already in use by another affiliate
        if (!existingAffiliate.getEmail().equals(affiliateDTO.getEmail()) &&
                affiliateRepository.existsByEmail(affiliateDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use: " + affiliateDTO.getEmail());
        }

        existingAffiliate.setName(affiliateDTO.getName());
        existingAffiliate.setEmail(affiliateDTO.getEmail());

        Affiliate updatedAffiliate = affiliateRepository.save(existingAffiliate);
        return convertToDTO(updatedAffiliate);
    }

    @Transactional
    public void delete(Long id) {
        if (!affiliateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Affiliate not found with id: " + id);
        }
        affiliateRepository.deleteById(id);
    }

    private AffiliateDTO convertToDTO(Affiliate affiliate) {
        AffiliateDTO dto = new AffiliateDTO();
        dto.setId(affiliate.getId());
        dto.setName(affiliate.getName());
        dto.setEmail(affiliate.getEmail());
        return dto;
    }

    private Affiliate convertToEntity(AffiliateDTO dto) {
        Affiliate affiliate = new Affiliate();
        affiliate.setId(dto.getId());
        affiliate.setName(dto.getName());
        affiliate.setEmail(dto.getEmail());
        return affiliate;
    }
}