package com.sys.ims.service;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NDesignation;

import java.util.List;
import java.util.UUID;

public interface NDesignationService {
    NDesignation createNDesignation(NDesignation nDesignation);
    NDesignation getNDesignationById(UUID id) throws BaseException;
    List<NDesignation> getNDesignationsByClient(UUID clientId) throws BaseException;
    NDesignation updateNDesignation(UUID id, NDesignation nDesignationDetails) throws BaseException;
    void deleteNDesignation(UUID id) throws BaseException;
}
