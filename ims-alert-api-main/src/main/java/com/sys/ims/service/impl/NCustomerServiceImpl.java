package com.sys.ims.service.impl;


import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import com.sys.ims.model.NCustomer;
import com.sys.ims.repository.ClientRepository;
import com.sys.ims.repository.NCustomerRepository;
import com.sys.ims.service.NCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NCustomerServiceImpl implements NCustomerService {

    private final NCustomerRepository nCustomerRepository;
    private final ClientRepository clientRepository;

    @Override
    public NCustomer createNCustomer(NCustomer nCustomer) {
        nCustomer.setCreatedAt(LocalDateTime.now());
        return nCustomerRepository.save(nCustomer);
    }

    @Override
    public NCustomer getNCustomerById(UUID id) throws BaseException {
        return nCustomerRepository.findById(id)
                .orElseThrow(() -> new BaseException("NCustomer not found with id: " + id));
    }

    @Override
    public List<NCustomer> getNCustomersByClient(UUID clientId) throws BaseException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BaseException("Client not found with id: " + clientId));
        return nCustomerRepository.findByClient(client);
    }

    @Override
    public NCustomer updateNCustomer(UUID id, NCustomer nCustomerDetails) throws BaseException {
        NCustomer nCustomer = getNCustomerById(id);
        nCustomer.setFullName(nCustomerDetails.getFullName());
        nCustomer.setEmail(nCustomerDetails.getEmail());
        nCustomer.setPhone(nCustomerDetails.getPhone());
        nCustomer.setAddress(nCustomerDetails.getAddress());
        nCustomer.setNationalId(nCustomerDetails.getNationalId());
        return nCustomerRepository.save(nCustomer);
    }

    @Override
    public void deleteNCustomer(UUID id) throws BaseException {
        NCustomer nCustomer = getNCustomerById(id);
        nCustomerRepository.delete(nCustomer);
    }
}