package com.igor.company.service;

import com.igor.company.Interface.IClientService;
import com.igor.company.exceptions.ApiRequestException;
import com.igor.company.model.Client;
import com.igor.company.model.dto.ClientRq;
import com.igor.company.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements IClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ResponseEntity<?> getAll(){
        try{
            return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Client> getClientByID(Long id){
        Optional<Client> client =  clientRepository.findById(id);
        if(client.isPresent()){
            return new ResponseEntity<>(client.get() ,HttpStatus.OK);
        }else{
            throw new ApiRequestException("Client does not exist");
        }
    }

    @Override
    public ResponseEntity<?> insertClient(ClientRq client){
        int countRg = getCountRg(client);
        if(countRg == 0) {
            Client clientSave = getClient(client);
            try{
                var response = clientRepository.save(clientSave);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }catch (Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
            throw new ApiRequestException(("RG are already registered"));
        }
    }

    @Override
    public ResponseEntity<?> updateClient(ClientRq client, Long id){
        int countId = getCountId(id);
        int countRg = getCountRg(client);

        if(countRg == 1 && countId == 1){
            boolean sameRg = sameRg(id, client.getRG());
            if(!sameRg){
                throw new ApiRequestException("RG are already registered");
            }
        }

        if(countId == 1) {
            Client clientSave = getClient(client);
            clientSave.setId(id);
            try{
                var response = clientRepository.save(clientSave);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
        throw new ApiRequestException(("Id does not exist"));
        }

    }

    @Override
    public ResponseEntity<?> deleteClient(Long id) {
        int countId = getCountId(id);
        if (countId == 1) {
            try{
                clientRepository.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.OK);
            }catch (Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
            throw new ApiRequestException("Id does not exist");
        }
    }

    private int getCountId(Long id) {
        return clientRepository.countById(id);
    }

    private Client getClient(ClientRq client) {
        Client clientSave = new Client();
        clientSave.setName(client.getName());
        clientSave.setAddress(client.getAddress());
        clientSave.setDebt(client.getDebt());
        clientSave.setEmail(client.getEmail());
        clientSave.setRG(client.getRG());
        return clientSave;
    }

    private int getCountRg(ClientRq client) {
        return clientRepository.countByRG(client.getRG());
    }

    private boolean sameRg(Long id, String RG){
        Client currentlyClient = clientRepository.getById(id);
        return RG.equals(currentlyClient.getRG());
    }

}
