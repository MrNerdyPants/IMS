package com.sys.ims.controller;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NDesignation;
import com.sys.ims.service.NDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ndesignations")
@RequiredArgsConstructor
public class NDesignationController {

    private final NDesignationService nDesignationService;

    @PostMapping
    public ResponseEntity<NDesignation> createNDesignation(@RequestBody NDesignation nDesignation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nDesignationService.createNDesignation(nDesignation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NDesignation> getNDesignationById(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(nDesignationService.getNDesignationById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<NDesignation>> getNDesignationsByClient(@PathVariable UUID clientId) throws BaseException {
        return ResponseEntity.ok(nDesignationService.getNDesignationsByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NDesignation> updateNDesignation(@PathVariable UUID id, @RequestBody NDesignation nDesignation) throws BaseException {
        return ResponseEntity.ok(nDesignationService.updateNDesignation(id, nDesignation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNDesignation(@PathVariable UUID id) throws BaseException {
        nDesignationService.deleteNDesignation(id);
        return ResponseEntity.noContent().build();
    }
}
