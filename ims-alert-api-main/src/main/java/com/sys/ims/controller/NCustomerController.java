package com.sys.ims.controller;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NCustomer;
import com.sys.ims.service.NCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ncustomers")
@RequiredArgsConstructor
public class NCustomerController {

    private final NCustomerService nCustomerService;

    @PostMapping
    public ResponseEntity<NCustomer> createNCustomer(@RequestBody NCustomer nCustomer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nCustomerService.createNCustomer(nCustomer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NCustomer> getNCustomerById(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(nCustomerService.getNCustomerById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<NCustomer>> getNCustomersByClient(@PathVariable UUID clientId) throws BaseException {
        return ResponseEntity.ok(nCustomerService.getNCustomersByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NCustomer> updateNCustomer(@PathVariable UUID id, @RequestBody NCustomer nCustomer) throws BaseException {
        return ResponseEntity.ok(nCustomerService.updateNCustomer(id, nCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNCustomer(@PathVariable UUID id) throws BaseException {
        nCustomerService.deleteNCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
