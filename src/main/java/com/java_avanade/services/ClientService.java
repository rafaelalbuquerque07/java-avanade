package com.java_avanade.services;

import com.java_avanade.dtos.ClientDTO;
import com.java_avanade.entities.Client;
import com.java_avanade.exceptions.ClientNotFoundException;
import com.java_avanade.exceptions.ResourceAlreadyExistsException;
import com.java_avanade.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios relacionada a clientes.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Retorna todos os clientes cadastrados.
     */
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo ID.
     * @throws ClientNotFoundException se o cliente não for encontrado
     */
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        return convertToDTO(client);
    }

    /**
     * Cria um novo cliente.
     * @throws ResourceAlreadyExistsException se o email já estiver em uso
     */
    @Transactional
    public ClientDTO create(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Client already exists with email: " + clientDTO.getEmail());
        }

        Client client = convertToEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    /**
     * Atualiza um cliente existente.
     * @throws ClientNotFoundException se o cliente não for encontrado
     * @throws ResourceAlreadyExistsException se o email já estiver em uso por outro cliente
     */
    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        // Verifica se o email já está em uso por outro cliente
        if (!existingClient.getEmail().equals(clientDTO.getEmail()) &&
                clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use: " + clientDTO.getEmail());
        }

        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());

        Client updatedClient = clientRepository.save(existingClient);
        return convertToDTO(updatedClient);
    }

    /**
     * Remove um cliente pelo ID.
     * @throws ClientNotFoundException se o cliente não for encontrado
     */
    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Client para um DTO ClientDTO.
     */
    private ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        return dto;
    }

    /**
     * Converte um DTO ClientDTO para uma entidade Client.
     */
    private Client convertToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        return client;
    }
}