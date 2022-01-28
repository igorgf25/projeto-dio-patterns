package com.igor.company.Interface;

import com.igor.company.model.Client;
import com.igor.company.model.dto.ClientRq;
import org.springframework.http.ResponseEntity;

public interface IClientService {
    public ResponseEntity<?> getAll();
    public ResponseEntity<Client> getClientByID(Long id);
    public ResponseEntity<?> insertClient(ClientRq client);
    public ResponseEntity<?> updateClient(ClientRq client, Long id);
    public ResponseEntity<?> deleteClient(Long id);
}
