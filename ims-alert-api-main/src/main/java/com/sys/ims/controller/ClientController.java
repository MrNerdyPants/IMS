package com.sys.ims.controller;

import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Client;
import com.sys.ims.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) throws BaseException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody Client client) throws BaseException {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) throws BaseException {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}