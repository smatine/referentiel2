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
@Table(name = "sgs")
public class Sg extends AuditModel {
    @Id
    @GeneratedValue(generator = "sg_generator")
    @SequenceGenerator(
            name = "sg_generator",
            sequenceName = "sg_sequence",
            initialValue = 1000
    )
    private Long id;

   
    @NotBlank
    @Column(unique=true, nullable=false) 
    private String name;
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vpc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vpc vpc;

	

	public Vpc getVpc() {
		return vpc;
	}

	public void setVpc(Vpc vpc) {
		this.vpc = vpc;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "text")
    private String text;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
	
    private String nameTag;


	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}
	
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sg_lb", 
    joinColumns = { @JoinColumn(name = "sg_id", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "lb_id", nullable = false, updatable = false) })
	@JsonIgnoreProperties({ "sgs"})
    private List<Lb> lbs = new ArrayList<>();


	public List<Lb> getLbs() {
		return lbs;
	}

	public void setLbs(List<Lb> lbs) {
		this.lbs = lbs;
	}
	
	/*
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sg_ecc", 
    joinColumns = { @JoinColumn(name = "sg_id", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "ecc_id", nullable = false, updatable = false) })
	@JsonIgnoreProperties({ "sgs"})
    private List<Ecc> eccs = new ArrayList<>();


	public List<Ecc> getEccs() {
		return eccs;
	}

	public void setEccs(List<Ecc> eccs) {
		this.eccs = eccs;
	}
	
	*/
}
