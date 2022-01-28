package com.igor.company.controller;

import com.igor.company.model.Client;
import com.igor.company.model.dto.ClientRq;
import com.igor.company.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class clientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") Long id){
        return clientService.getClientByID(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> insertClient(@RequestBody ClientRq client){
        return clientService.insertClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@RequestBody ClientRq client, @PathVariable("id") Long id){
        return clientService.updateClient(client, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
        return clientService.deleteClient(id);
    }
}
