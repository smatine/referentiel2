package com.example.referentiel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cascade;
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
@Table(name = "subnetgroups")

public class SubnetGroup extends AuditModel {
    @Id
    @GeneratedValue(generator = "subnetGroup_generator")
    @SequenceGenerator(
            name = "subnetGroup_generator",
            sequenceName = "subnetGroup_sequence",
            initialValue = 1000
    )
    private Long id;

   
    @NotBlank
    @Size(min = 3, max = 3, message="Type be RDS|EFS|ECC|ELS")
	@ApiModelProperty(notes="Env should be RDS|EFS|ECC|ELS")
    private String type;

	
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@NotBlank
    @Column(unique=true, nullable=false) 
    private String name;
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@ManyToOne (fetch = FetchType.EAGER, /*cascade=CascadeType.ALL,*/ optional = false) //(fetch = FetchType.LAZY, optional = false)
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

    
    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "subnetgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Subnet> subnets = new ArrayList<>();


	public List<Subnet> getSubnets() {
		return subnets;
	}

	public void setSubnets(List<Subnet> subnets) {
		this.subnets = subnets;
	}

    
	@OneToMany(fetch = FetchType.LAZY, 
            mappedBy = "subnetgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnoreProperties({ "subnetgroup"})
    private List<Rds> rdss = new ArrayList<>();


	public List<Rds> getRdss() {
		return rdss;
	}

	public void setRdss(List<Rds> rdss) {
		this.rdss = rdss;
	}


	@OneToMany(fetch = FetchType.LAZY, 
            mappedBy = "subnetgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnoreProperties({ "subnetgroup"})
    private List<Efs> efss = new ArrayList<>();


	public List<Efs> getEfss() {
		return efss;
	}

	public void setEfss(List<Efs> efss) {
		this.efss = efss;
	}


	@OneToMany(fetch = FetchType.LAZY, 
            mappedBy = "subnetgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnoreProperties({ "subnetgroup"})
    private List<ElasticCache> elasticcaches = new ArrayList<>();


	
	
	public List<ElasticCache> getElasticcaches() {
		return elasticcaches;
	}

	public void setElasticcaches(List<ElasticCache> elasticcaches) {
		this.elasticcaches = elasticcaches;
	}

	@OneToMany(fetch = FetchType.LAZY, 
            mappedBy = "subnetgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnoreProperties({ "subnetgroup"})
    private List<ElasticSearch> elasticsearchs = new ArrayList<>();

	public List<ElasticSearch> getElasticsearchs() {
		return elasticsearchs;
	}

	public void setElasticsearchs(List<ElasticSearch> elasticsearchs) {
		this.elasticsearchs = elasticsearchs;
	}

}
