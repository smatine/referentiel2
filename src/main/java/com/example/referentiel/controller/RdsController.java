package com.example.referentiel.controller;

import com.example.referentiel.exception.ResourceNotFoundException;
import com.example.referentiel.model.Rds;
import com.example.referentiel.model.Subnet;
import com.example.referentiel.model.SubnetGroup;
import com.example.referentiel.model.Vpc;
import com.example.referentiel.repository.RdsRepository;
import com.example.referentiel.repository.SubnetGroupRepository;
import com.example.referentiel.repository.VpcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class RdsController {

    @Autowired
    private RdsRepository rdsRepository;

    @Autowired
    private VpcRepository vpcRepository;
    
    @Autowired
    private SubnetGroupRepository subnetGroupRepository;

    @GetMapping("/vpcs/{vpcId}/rdss")
    public List<Rds> getRdssByVpcId(@PathVariable Long vpcId) {
        return rdsRepository.findByVpcId(vpcId);
    }

    @GetMapping("/rdss")
    Collection<Rds> rdss() {
    	Collection<Rds> rdss = rdsRepository.findAll();
    	
    	Iterator<Rds> it = rdss.iterator();
		 while(it.hasNext()) {
			Rds rds = (Rds)it.next();
			SubnetGroup sb = rds.getSubnetgroup();
		 }    	
        return rdss;
    }
    
    @GetMapping("/rdss/{id}")
    ResponseEntity<?> getRds(@PathVariable Long id) {
        Optional<Rds> rds = rdsRepository.findById(id);
        
        //System.out.println("rds:" +  rds.toString());
        
        return rds.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/vpcs/{vpcId}/rdss")
    public Rds addRds(@PathVariable String vpcId,
                            @Valid @RequestBody Rds rds) {
    	
    	long accId = Long.valueOf(vpcId);
        return vpcRepository.findById(accId)
                .map(vpc -> {
                	rds.setVpc(vpc);
                    return rdsRepository.save(rds);
                }).orElseThrow(() -> new ResourceNotFoundException("Vpc not found with id " + vpcId));
    }

    @PutMapping("/vpcs/{vpcId}/rdss/{rdsId}")
    public Rds updateRds(@PathVariable Long vpcId,
                               @PathVariable Long rdsId,
                               @Valid @RequestBody Rds rdsRequest) {
        if(!vpcRepository.existsById(vpcId)) {
            throw new ResourceNotFoundException("Vpc not found with id " + vpcId);
        }
        Optional<Vpc> vpc = vpcRepository.findById(vpcId);
        Optional<SubnetGroup> sg = subnetGroupRepository.findById(rdsRequest.getSubnetgroup().getId());
        
        return rdsRepository.findById(rdsId)
                .map(rds -> {
                	rds.setText(rdsRequest.getText());
                	rds.setName(rdsRequest.getName());
                	
                	rds.setVpc(vpc.get());
                	rds.setSubnetgroup(sg.get());
                	
                    return rdsRepository.save(rds);
                }).orElseThrow(() -> new ResourceNotFoundException("Rds not found with id " + rdsId));
    }

    @DeleteMapping("/vpcs/{vpcId}/rdss/{rdsId}")
    public ResponseEntity<?> deleteRds(@PathVariable Long vpcId,
                                          @PathVariable Long rdsId) {
        if(!vpcRepository.existsById(vpcId)) {
            throw new ResourceNotFoundException("Vpc not found with id " + vpcId);
        }

        return rdsRepository.findById(rdsId)
                .map(rds -> {
                	rdsRepository.delete(rds);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Rds not found with id " + rdsId));

    }
}
