package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.ClientDTO;
import com.jonathas.drinkeasy.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){this.clientService = clientService;}

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") UUID id){
        Optional<ClientDTO> client = clientService.findById(id);
        return ResponseEntity.ok(client.get());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> post(@RequestBody ClientDTO clientDTO){
        Optional<ClientDTO> client = Optional.ofNullable(clientService.save(clientDTO));
        return new ResponseEntity<>(client.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> put(@PathVariable("id") UUID id, @RequestBody ClientDTO clientDTO){
        ClientDTO updatedClient = clientService.update(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
