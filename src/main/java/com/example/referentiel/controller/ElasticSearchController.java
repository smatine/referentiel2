package com.example.referentiel.controller;

import com.example.referentiel.exception.ResourceNotFoundException;
import com.example.referentiel.model.ElasticSearch;
import com.example.referentiel.model.Rds;
import com.example.referentiel.model.SubnetGroup;
import com.example.referentiel.model.Vpc;
import com.example.referentiel.repository.ElasticSearchRepository;
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
public class ElasticSearchController {

    @Autowired
    private ElasticSearchRepository elasticSearchRepository;

    @Autowired
    private VpcRepository vpcRepository;
    
    @Autowired
    private SubnetGroupRepository subnetGroupRepository;

    @GetMapping("/vpcs/{vpcId}/elasticSearchs")
    public List<ElasticSearch> getElasticSearchsByVpcId(@PathVariable Long vpcId) {
        return elasticSearchRepository.findByVpcId(vpcId);
    }

    @GetMapping("/elasticSearchs")
    Collection<ElasticSearch> elasticSearchs() {
    	
        Collection<ElasticSearch> elasticSearchs = elasticSearchRepository.findAll();
    	
    	Iterator<ElasticSearch> it = elasticSearchs.iterator();
		 while(it.hasNext()) {
			ElasticSearch elasticSearch = (ElasticSearch)it.next();
			SubnetGroup sb = elasticSearch.getSubnetgroup();
		 }    	
        return elasticSearchs;
    }
    
    @GetMapping("/elasticSearchs/{id}")
    ResponseEntity<?> getElasticSearch(@PathVariable Long id) {
        Optional<ElasticSearch> elasticSearch = elasticSearchRepository.findById(id);
        
        System.out.println("elasticSearch:" +  elasticSearch.toString());
        
        return elasticSearch.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/vpcs/{vpcId}/elasticSearchs")
    public ElasticSearch addElasticSearch(@PathVariable String vpcId,
                            @Valid @RequestBody ElasticSearch elasticSearch) {
    	
    	long accId = Long.valueOf(vpcId);
        return vpcRepository.findById(accId)
                .map(vpc -> {
                	elasticSearch.setVpc(vpc);
                    return elasticSearchRepository.save(elasticSearch);
                }).orElseThrow(() -> new ResourceNotFoundException("Vpc not found with id " + vpcId));
    }

    @PostMapping("/elasticSearch")
    public ElasticSearch addElasticSearchPublic(
                            @Valid @RequestBody ElasticSearch elasticSearch) {
    	
    	return elasticSearchRepository.save(elasticSearch);
    }
    
    @PutMapping("/vpcs/{vpcId}/elasticSearchs/{elasticSearchId}")
    public ElasticSearch updateElasticSearch(@PathVariable Long vpcId,
                               @PathVariable Long elasticSearchId,
                               @Valid @RequestBody ElasticSearch elasticSearchRequest) {
        if(!vpcRepository.existsById(vpcId)) {
            throw new ResourceNotFoundException("Vpc not found with id " + vpcId);
        }
        Optional<Vpc> vpc = vpcRepository.findById(vpcId);
        Optional<SubnetGroup> sg = subnetGroupRepository.findById(elasticSearchRequest.getSubnetgroup().getId());
        
        return elasticSearchRepository.findById(elasticSearchId)
                .map(elasticSearch -> {
                	elasticSearch.setText(elasticSearchRequest.getText());
                	elasticSearch.setName(elasticSearchRequest.getName());
                	elasticSearch.setPrive(elasticSearchRequest.isPrive());
                	elasticSearch.setVpc(vpc.get());
                	elasticSearch.setSubnetgroup(sg.get());
                    return elasticSearchRepository.save(elasticSearch);
                }).orElseThrow(() -> new ResourceNotFoundException("ElasticSearch not found with id " + elasticSearchId));
    }
 
    @PutMapping("/elasticSearch/{elasticSearchId}")
    public ElasticSearch updateElasticSearchPublic(
                               @PathVariable Long elasticSearchId,
                               @Valid @RequestBody ElasticSearch elasticSearchRequest) {
        
        return elasticSearchRepository.findById(elasticSearchId)
                .map(elasticSearch -> {
                	elasticSearch.setText(elasticSearchRequest.getText());
                	elasticSearch.setName(elasticSearchRequest.getName());
                	elasticSearch.setPrive(elasticSearchRequest.isPrive());
                    return elasticSearchRepository.save(elasticSearch);
                }).orElseThrow(() -> new ResourceNotFoundException("ElasticSearch not found with id " + elasticSearchId));
    }

    @DeleteMapping("/vpcs/{vpcId}/elasticSearchs/{elasticSearchId}")
    public ResponseEntity<?> deleteElasticSearch(@PathVariable Long vpcId,
                                          @PathVariable Long elasticSearchId) {
        if(!vpcRepository.existsById(vpcId)) {
            throw new ResourceNotFoundException("Vpc not found with id " + vpcId);
        }

        return elasticSearchRepository.findById(elasticSearchId)
                .map(elasticSearch -> {
                	elasticSearchRepository.delete(elasticSearch);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("ElasticSearch not found with id " + elasticSearchId));

    }
    
    @DeleteMapping("/elasticSearch/{elasticSearchId}")
    public ResponseEntity<?> deleteElasticSearchPublic(
                                          @PathVariable Long elasticSearchId) {
        
        return elasticSearchRepository.findById(elasticSearchId)
                .map(elasticSearch -> {
                	elasticSearchRepository.delete(elasticSearch);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("ElasticSearch not found with id " + elasticSearchId));

    }
}
