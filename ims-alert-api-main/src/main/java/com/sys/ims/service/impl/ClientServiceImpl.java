package com.sys.ims.service.impl;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import com.sys.ims.repository.ClientRepository;
import com.sys.ims.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(UUID id) throws BaseException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new BaseException("Client not found with id: " + id));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(UUID id, Client clientDetails) throws BaseException {
        Client client = getClientById(id);
        client.setCompanyName(clientDetails.getCompanyName());
        client.setEmail(clientDetails.getEmail());
        client.setPhone(clientDetails.getPhone());
        client.setStatus(clientDetails.getStatus());
        client.setModulesEnabled(clientDetails.getModulesEnabled());
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(UUID id) throws BaseException {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }
}
