package com.example.referentiel.controller;

import com.example.referentiel.exception.ResourceNotFoundException;
import com.example.referentiel.model.Tag;
import com.example.referentiel.model.TargetGroup;
import com.example.referentiel.model.Ecc;
import com.example.referentiel.model.Lb;
import com.example.referentiel.model.Nacl;
import com.example.referentiel.model.Peering;
import com.example.referentiel.model.RouteTable;
import com.example.referentiel.model.Sg;
import com.example.referentiel.model.Subnet;
import com.example.referentiel.repository.TagRepository;
import com.example.referentiel.repository.TargetGroupRepository;
import com.example.referentiel.repository.EccRepository;
import com.example.referentiel.repository.LbRepository;
import com.example.referentiel.repository.NaclRepository;
import com.example.referentiel.repository.PeeringRepository;
import com.example.referentiel.repository.RouteTableRepository;
import com.example.referentiel.repository.SgRepository;

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
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NaclRepository naclRepository;
    
    @Autowired
    private SgRepository sgRepository;
    
    @Autowired
    private RouteTableRepository routeTableRepository;
    
    @Autowired
    private PeeringRepository peeringRepository; 
    
    @Autowired
    private TargetGroupRepository targetGroupRepository;
    
    @Autowired
    private LbRepository lbRepository;
    
    @Autowired
    private EccRepository eccRepository;
    
    @GetMapping("/nacls/{naclId}/tags")
    public List<Tag> getTagsByNaclId(@PathVariable Long naclId) {
    	List<Tag> tags = tagRepository.findByNaclId(naclId);
    	
    	/*Iterator<Tag> itt = tags.iterator();
    	while(itt.hasNext()) {
    		Tag tag = (Tag)itt.next();
    		tag.setNacl(tag.getNacl());
    	} */
        return tags;
    }
    
    @GetMapping("/tags")
    Collection<Tag> tags() {
    	Collection<Tag> tags = tagRepository.findAll();  	
        return tags;
    }
    
    @GetMapping("/tags/{id}")
    ResponseEntity<?> getTag(@PathVariable Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
           
        return tag.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/nacls/{naclId}/tags")
    public Tag addTag(@PathVariable String naclId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(naclId);
        return naclRepository.findById(accId)
                .map(nacl -> {
                	tag.setNacl(nacl);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Nacl not found with id " + naclId));
    }

    @PutMapping("/nacls/{naclId}/tags/{tagId}")
    public Tag updateTag(@PathVariable Long naclId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!naclRepository.existsById(naclId)) {
            throw new ResourceNotFoundException("Nacl not found with id " + naclId);
        }
        Optional<Nacl> nacl = naclRepository.findById(naclId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setNacl(nacl.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/nacls/{naclId}/tags/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable Long naclId,
                                          @PathVariable Long tagId) {
        if(!naclRepository.existsById(naclId)) {
            throw new ResourceNotFoundException("Nacl not found with id " + naclId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }
    
    // Sg
    
    @GetMapping("/sgs/{sgId}/tags")
    public List<Tag> getTagsBySgId(@PathVariable Long sgId) {
        return tagRepository.findBySgId(sgId);
    }
    
    @PostMapping("/sgs/{sgId}/tags")
    public Tag addTagSg(@PathVariable String sgId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(sgId);
        return sgRepository.findById(accId)
                .map(sg -> {
                	tag.setSg(sg);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Sg not found with id " + sgId));
    }

    @PutMapping("/sgs/{sgId}/tags/{tagId}")
    public Tag updateTagSg(@PathVariable Long sgId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!sgRepository.existsById(sgId)) {
            throw new ResourceNotFoundException("Sg not found with id " + sgId);
        }
        Optional<Sg> sg = sgRepository.findById(sgId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setSg(sg.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/sgs/{sgId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagSg(@PathVariable Long sgId,
                                          @PathVariable Long tagId) {
        if(!sgRepository.existsById(sgId)) {
            throw new ResourceNotFoundException("Sg not found with id " + sgId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }
    
    //routetable
    
    @GetMapping("/routeTables/{routetableId}/tags")
    public List<Tag> getTagsByRoutetableId(@PathVariable Long routetableId) {
        return tagRepository.findByRouteTableId(routetableId);
    }
    
    @PostMapping("/routeTables/{routetableId}/tags")
    public Tag addTagRoutetable(@PathVariable String routetableId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(routetableId);
        return routeTableRepository.findById(accId)
                .map(routetable -> {
                	tag.setRouteTable(routetable);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Routetable not found with id " + routetableId));
    }

    @PutMapping("/routeTables/{routetableId}/tags/{tagId}")
    public Tag updateTagRoutetable(@PathVariable Long routetableId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!routeTableRepository.existsById(routetableId)) {
            throw new ResourceNotFoundException("Routetable not found with id " + routetableId);
        }
        Optional<RouteTable> routetable = routeTableRepository.findById(routetableId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setRouteTable(routetable.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/routeTables/{routetableId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagRoutetable(@PathVariable Long routetableId,
                                          @PathVariable Long tagId) {
        if(!routeTableRepository.existsById(routetableId)) {
            throw new ResourceNotFoundException("Routetable not found with id " + routetableId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }
    
    
 //peering
    
    @GetMapping("/peerings/{peeringId}/tags")
    public List<Tag> getTagsByPeeringId(@PathVariable Long peeringId) {
    	
    	/*List<Tag> lTags = tagRepository.findByPeeringId(peeringId);
    	Iterator<Tag> itt = lTags.iterator();
    	while(itt.hasNext()) {
    		Tag tag = (Tag)itt.next();
    		Peering peering = tag.getPeering();
    		System.out.println("getTagsByPeeringId:" + "tag:" + tag.getKey() + " :peering" + peering.getId());
    	} */
        return tagRepository.findByPeeringId(peeringId);
    }
    
    @PostMapping("/peerings/{peeringId}/tags")
    public Tag addTagPeering(@PathVariable String peeringId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(peeringId);
        return peeringRepository.findById(accId)
                .map(peering -> {
                	tag.setPeering(peering);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("peering not found with id " + peeringId));
    }

    @PutMapping("/peerings/{peeringId}/tags/{tagId}")
    public Tag updateTagPeering(@PathVariable Long peeringId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!peeringRepository.existsById(peeringId)) {
            throw new ResourceNotFoundException("peering not found with id " + peeringId);
        }
        Optional<Peering> peering = peeringRepository.findById(peeringId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setPeering(peering.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/peerings/{peeringId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagPeering(@PathVariable Long peeringId,
                                          @PathVariable Long tagId) {
        if(!peeringRepository.existsById(peeringId)) {
            throw new ResourceNotFoundException("peering not found with id " + peeringId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }

    
//targetgroup
    
    @GetMapping("/targetGroups/{targetGroupId}/tags")
    public List<Tag> getTagsByTargetGroupId(@PathVariable Long targetGroupId) {
    	
        return tagRepository.findByTargetGroupId(targetGroupId);
    }
    
    @PostMapping("/targetGroups/{targetGroupId}/tags")
    public Tag addTagTargetGroup(@PathVariable String targetGroupId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(targetGroupId);
        return targetGroupRepository.findById(accId)
                .map(targetGroup -> {
                	tag.setTargetGroup(targetGroup);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("targetGroup not found with id " + targetGroupId));
    }

    @PutMapping("/targetGroups/{targetGroupId}/tags/{tagId}")
    public Tag updateTagTargetGroup(@PathVariable Long targetGroupId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!targetGroupRepository.existsById(targetGroupId)) {
            throw new ResourceNotFoundException("targetGroup not found with id " + targetGroupId);
        }
        Optional<TargetGroup> targetGroup = targetGroupRepository.findById(targetGroupId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setTargetGroup(targetGroup.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/targetGroups/{targetGroupId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagTargetGroup(@PathVariable Long targetGroupId,
                                          @PathVariable Long tagId) {
        if(!targetGroupRepository.existsById(targetGroupId)) {
            throw new ResourceNotFoundException("targetGroup not found with id " + targetGroupId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }
    
//lb
    
    @GetMapping("/lbs/{lbId}/tags")
    public List<Tag> getTagsByLbId(@PathVariable Long lbId) {
    	
        return tagRepository.findByLbId(lbId);
    }
    
    @PostMapping("/lbs/{lbId}/tags")
    public Tag addTagLb(@PathVariable String lbId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(lbId);
        return lbRepository.findById(accId)
                .map(lb -> {
                	tag.setLb(lb);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("lb not found with id " + lbId));
    }

    @PutMapping("/lbs/{lbId}/tags/{tagId}")
    public Tag updateTagLb(@PathVariable Long lbId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!lbRepository.existsById(lbId)) {
            throw new ResourceNotFoundException("lb not found with id " + lbId);
        }
        Optional<Lb> lb = lbRepository.findById(lbId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setLb(lb.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/lbs/{lbId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagLb(@PathVariable Long lbId,
                                          @PathVariable Long tagId) {
        if(!lbRepository.existsById(lbId)) {
            throw new ResourceNotFoundException("lb not found with id " + lbId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }

    
//ecc
    
    @GetMapping("/eccs/{eccId}/tags")
    public List<Tag> getTagsByEccId(@PathVariable Long eccId) {
    	
        return tagRepository.findByEccId(eccId);
    }
    
    @PostMapping("/eccs/{eccId}/tags")
    public Tag addTagEcc(@PathVariable String eccId,
                            @Valid @RequestBody Tag tag) {
    	
    	long accId = Long.valueOf(eccId);
        return eccRepository.findById(accId)
                .map(ecc -> {
                	tag.setEcc(ecc);
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("ecc not found with id " + eccId));
    }

    @PutMapping("/eccs/{eccId}/tags/{tagId}")
    public Tag updateTagEcc(@PathVariable Long eccId,
                               @PathVariable Long tagId,
                               @Valid @RequestBody Tag tagRequest) {
        if(!eccRepository.existsById(eccId)) {
            throw new ResourceNotFoundException("ecc not found with id " + eccId);
        }
        Optional<Ecc> ecc = eccRepository.findById(eccId);
        
        return tagRepository.findById(tagId)
                .map(tag -> {
                	tag.setText(tagRequest.getText());
                	tag.setKey(tagRequest.getKey());
                	tag.setValue(tagRequest.getValue());
                	tag.setEcc(ecc.get());
                	
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));
    }

    @DeleteMapping("/eccs/{eccId}/tags/{tagId}")
    public ResponseEntity<?> deleteTagEcc(@PathVariable Long eccId,
                                          @PathVariable Long tagId) {
        if(!eccRepository.existsById(eccId)) {
            throw new ResourceNotFoundException("ecc not found with id " + eccId);
        }

        return tagRepository.findById(tagId)
                .map(tag -> {
                	tagRepository.delete(tag);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + tagId));

    }

}
