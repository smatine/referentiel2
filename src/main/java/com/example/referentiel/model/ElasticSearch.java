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
@Table(name = "elasticSearchs")
public class ElasticSearch extends AuditModel {
    @Id
    @GeneratedValue(generator = "elasticSearch_generator")
    @SequenceGenerator(
            name = "elasticSearch_generator",
            sequenceName = "elasticSearch_sequence",
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
	
    private boolean isPrive = true;
    
	public boolean isPrive() {
		return isPrive;
	}

	public void setPrive(boolean isPrive) {
		this.isPrive = isPrive;
	}

	@ManyToOne (fetch = FetchType.EAGER, /*cascade=CascadeType.ALL,*/ optional = true) //(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vpc_id", nullable = true)
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

    @ManyToOne (fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "subnetgroup_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({ "elasticsearchs"})
    private SubnetGroup subnetgroup;


	public SubnetGroup getSubnetgroup() {
		return subnetgroup;
	}

	public void setSubnetgroup(SubnetGroup subnetgroup) {
		this.subnetgroup = subnetgroup;
	}

}
