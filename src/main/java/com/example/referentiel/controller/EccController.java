package com.example.referentiel.controller;

import com.example.referentiel.exception.ResourceNotFoundException;
import com.example.referentiel.model.Ami;
import com.example.referentiel.model.Ecc;
import com.example.referentiel.model.InstanceType;
import com.example.referentiel.model.Subnet;
import com.example.referentiel.model.Vpc;
import com.example.referentiel.repository.AmiRepository;
import com.example.referentiel.repository.EccRepository;
import com.example.referentiel.repository.InstanceTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class EccController {

    @Autowired
    private EccRepository eccRepository;
    
    @Autowired
    private AmiRepository amiRepository;
    
    @Autowired
    private InstanceTypeRepository instanceTypeRepository;
    
    @GetMapping("/eccs")
    Collection<Ecc> eccs() {
    	Collection<Ecc> eccs = eccRepository.findAll();
        return eccs;
    }
    
    @GetMapping("/eccs/{id}")
    ResponseEntity<?> getEcc(@PathVariable Long id) {
        Optional<Ecc> ecc = eccRepository.findById(id);
        
        return ecc.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ecc")
    public Ecc addEcc(@Valid @RequestBody Ecc ecc) {
    	return eccRepository.save(ecc);
    }
    
    @PutMapping("/eccs/{eccId}")
    public Ecc updateSubnet(@PathVariable Long eccId,
                               @Valid @RequestBody Ecc eccRequest) {
    	
    	
        return eccRepository.findById(eccId)
                .map(ecc -> {
                	//autoAssignPublicIp shutdownBehaviour enableTerminationProtection encoded64 instanceType ami
                    //monitoring useData useDataText
                	
                	ecc.setAutoAssignPublicIp(eccRequest.getAutoAssignPublicIp());
                	ecc.setShutdownBehaviour(eccRequest.getShutdownBehaviour());
                	ecc.setEnableTerminationProtection(eccRequest.isEnableTerminationProtection());
                	ecc.setEncoded64(eccRequest.isEncoded64());
                	
                	ecc.setMonitoring(eccRequest.isMonitoring());
                	ecc.setUserData(eccRequest.isUserData());
                	ecc.setUserDataText(eccRequest.getUserDataText());
                	
                	//Ami ami = amiRepository.findById(eccRequest.getAmi().getId());
                	
                	ecc.setAmi(eccRequest.getAmi());
                	ecc.setInstanceType(eccRequest.getInstanceType());
                	ecc.setVpc(eccRequest.getVpc());
                	ecc.setSubnet(eccRequest.getSubnet());
                	
                    return eccRepository.save(ecc);
                }).orElseThrow(() -> new ResourceNotFoundException("Ecc not found with id " + eccId));
    }

    @DeleteMapping("/eccs/{eccId}")
    public ResponseEntity<?> deleteEcc(@PathVariable Long eccId) {

        return eccRepository.findById(eccId)
                .map(ecc -> {               	
                	System.out.println("deleteEcc");
                    /*
                	Ami ami = ecc.getAmi();
                	InstanceType instanceType = ecc.getInstanceType();
                	Vpc vpc = ecc.getVpc();
                	Subnet subnet = ecc.getSubnet();
                	*/
                	eccRepository.delete(ecc);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Ecc not found with id " + eccId));

    }
}
