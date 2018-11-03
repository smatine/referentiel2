package com.example.referentiel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "regions")
public class Region extends AuditModel {
    @Id
    @GeneratedValue(generator = "region_generator")
    @SequenceGenerator(
            name = "region_generator",
            sequenceName = "region_sequence",
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
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "text")
    private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    /*
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "region")
	@JsonIgnoreProperties("region")
    private Set<Account> accounts = new HashSet<>();


	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}*/
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "region")
	@JsonIgnoreProperties("region")
    private Set<Cidr> cidrs = new HashSet<>();


	public Set<Cidr> getCidrs() {
		return cidrs;
	}

	public void setCidrs(Set<Cidr> cidrs) {
		this.cidrs = cidrs;
	}

	/*
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "region")
	@JsonIgnoreProperties("region")
    private Set<Ami> amis = new HashSet<>();
	
	
	public Set<Ami> getAmis() {
		return amis;
	}

	public void setAmis(Set<Ami> amis) {
		this.amis = amis;
	}
    */
    
}
