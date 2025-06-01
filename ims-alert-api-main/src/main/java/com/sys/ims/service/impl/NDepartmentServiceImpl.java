package com.sys.ims.service.impl;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import com.sys.ims.model.NDepartment;
import com.sys.ims.model.NDesignation;
import com.sys.ims.repository.ClientRepository;
import com.sys.ims.repository.NDepartmentRepository;
import com.sys.ims.repository.NDesignationRepository;
import com.sys.ims.service.NDepartmentService;
import com.sys.ims.service.NDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NDepartmentServiceImpl implements NDepartmentService {

    private final NDepartmentRepository nDepartmentRepository;
    private final ClientRepository clientRepository;

    @Override
    public NDepartment createNDepartment(NDepartment nDepartment) {
        nDepartment.setCreatedAt(LocalDateTime.now());
        return nDepartmentRepository.save(nDepartment);
    }

    @Override
    public NDepartment getNDepartmentById(UUID id) throws BaseException {
        return nDepartmentRepository.findById(id)
                .orElseThrow(() -> new BaseException("NDepartment not found with id: " + id));
    }

    @Override
    public List<NDepartment> getNDepartmentsByClient(UUID clientId) throws BaseException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BaseException("Client not found with id: " + clientId));
        return nDepartmentRepository.findByClient(client);
    }

    @Override
    public NDepartment updateNDepartment(UUID id, NDepartment nDepartmentDetails) throws BaseException {
        NDepartment nDepartment = getNDepartmentById(id);
        nDepartment.setDepartmentName(nDepartmentDetails.getDepartmentName());
        return nDepartmentRepository.save(nDepartment);
    }

    @Override
    public void deleteNDepartment(UUID id) throws BaseException {
        NDepartment nDepartment = getNDepartmentById(id);
        nDepartmentRepository.delete(nDepartment);
    }
}
