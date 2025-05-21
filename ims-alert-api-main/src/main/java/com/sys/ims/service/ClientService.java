package com.sys.ims.service;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ClientService {
    Client createClient(Client client);
    Client getClientById(UUID id) throws BaseException;
    List<Client> getAllClients();
    Client updateClient(UUID id, Client clientDetails) throws BaseException;
    void deleteClient(UUID id) throws BaseException;

}
