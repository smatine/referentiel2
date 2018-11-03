package com.example.referentiel.controller;

import com.example.referentiel.exception.ResourceNotFoundException;
import com.example.referentiel.model.Account;
import com.example.referentiel.model.Storage;
import com.example.referentiel.repository.AccountRepository;
import com.example.referentiel.repository.StorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts/{accountId}/storages")
    public List<Storage> getStoragesByAccountId(@PathVariable Long accountId) {
        return storageRepository.findByAccountId(accountId);
    }

    @GetMapping("/storages")
    Collection<Storage> storages() {
    	
        return storageRepository.findAll();
    }
    
    @GetMapping("/storages/{id}")
    ResponseEntity<?> getStorage(@PathVariable Long id) {
        Optional<Storage> storage = storageRepository.findById(id);
        
        System.out.println("storage:" +  storage.toString());
        
        return storage.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/accounts/{accountId}/storages")
    public Storage addStorage(@PathVariable String accountId,
                            @Valid @RequestBody Storage storage) {
    	
    	long accId = Long.valueOf(accountId);
        return accountRepository.findById(accId)
                .map(account -> {
                	storage.setAccount(account);
                    return storageRepository.save(storage);
                }).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));
    }

    @PutMapping("/accounts/{accountId}/storages/{storageId}")
    public Storage updateStorage(@PathVariable Long accountId,
                               @PathVariable Long storageId,
                               @Valid @RequestBody Storage storageRequest) {
        if(!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found with id " + accountId);
        }
        Optional<Account> account = accountRepository.findById(accountId);
        return storageRepository.findById(storageId)
                .map(storage -> {
                	storage.setText(storageRequest.getText());
                	storage.setName(storageRequest.getName());
                	
                	storage.setAccount(account.get());
                	
                    return storageRepository.save(storage);
                }).orElseThrow(() -> new ResourceNotFoundException("Storage not found with id " + storageId));
    }

    @DeleteMapping("/accounts/{accountId}/storages/{storageId}")
    public ResponseEntity<?> deleteStorage(@PathVariable Long accountId,
                                          @PathVariable Long storageId) {
        if(!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found with id " + accountId);
        }

        return storageRepository.findById(storageId)
                .map(storage -> {
                	storageRepository.delete(storage);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Storage not found with id " + storageId));

    }
}
