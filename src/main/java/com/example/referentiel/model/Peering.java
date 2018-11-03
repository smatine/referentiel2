package com.example.referentiel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "peerings")
public class Peering extends AuditModel {
    @Id
    @GeneratedValue(generator = "peering_generator")
    @SequenceGenerator(
            name = "peering_generator",
            sequenceName = "peering_sequence",
            initialValue = 1000
    )
    private Long id;

   
    @NotBlank
    @Column(unique=true, nullable=false) 
    private String name;	
	
	@ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vpc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vpc vpc;


    @Column(columnDefinition = "text")
    private String text;

    @NotBlank
    private String type;
    
    @OneToOne (fetch = FetchType.EAGER,  optional = true)
	@JoinColumn(name = "peeringAccepterExternal_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PeeringAccepterExternal peeringAccepterExternal;
   
    @OneToOne (fetch = FetchType.EAGER,  optional = true)
	@JoinColumn(name = "peeringAccepterInternal_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PeeringAccepterInternal peeringAccepterInternal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vpc getVpc() {
		return vpc;
	}

	public void setVpc(Vpc vpc) {
		this.vpc = vpc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PeeringAccepterExternal getPeeringAccepterExternal() {
		return peeringAccepterExternal;
	}

	public void setPeeringAccepterExternal(PeeringAccepterExternal peeringAccepterExternal) {
		this.peeringAccepterExternal = peeringAccepterExternal;
	}

	public PeeringAccepterInternal getPeeringAccepterInternal() {
		return peeringAccepterInternal;
	}

	public void setPeeringAccepterInternal(PeeringAccepterInternal peeringAccepterInternal) {
		this.peeringAccepterInternal = peeringAccepterInternal;
	}
 
    
    
    
}
