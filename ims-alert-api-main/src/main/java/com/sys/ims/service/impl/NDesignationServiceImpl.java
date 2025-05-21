package com.sys.ims.service.impl;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import com.sys.ims.model.NDesignation;
import com.sys.ims.repository.ClientRepository;
import com.sys.ims.repository.NDesignationRepository;
import com.sys.ims.service.NDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NDesignationServiceImpl implements NDesignationService {

    private final NDesignationRepository nDesignationRepository;
    private final ClientRepository clientRepository;

    @Override
    public NDesignation createNDesignation(NDesignation nDesignation) {
        nDesignation.setCreatedAt(LocalDateTime.now());
        return nDesignationRepository.save(nDesignation);
    }

    @Override
    public NDesignation getNDesignationById(UUID id) throws BaseException {
        return nDesignationRepository.findById(id)
                .orElseThrow(() -> new BaseException("NDesignation not found with id: " + id));
    }

    @Override
    public List<NDesignation> getNDesignationsByClient(UUID clientId) throws BaseException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BaseException("Client not found with id: " + clientId));
        return nDesignationRepository.findByClient(client);
    }

    @Override
    public NDesignation updateNDesignation(UUID id, NDesignation nDesignationDetails) throws BaseException {
        NDesignation nDesignation = getNDesignationById(id);
        nDesignation.setDesignationName(nDesignationDetails.getDesignationName());
        return nDesignationRepository.save(nDesignation);
    }

    @Override
    public void deleteNDesignation(UUID id) throws BaseException {
        NDesignation nDesignation = getNDesignationById(id);
        nDesignationRepository.delete(nDesignation);
    }
}
