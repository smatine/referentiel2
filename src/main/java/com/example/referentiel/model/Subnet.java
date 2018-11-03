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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "subnets")
public class Subnet extends AuditModel {
    @Id
    @GeneratedValue(generator = "subnet_generator")
    @SequenceGenerator(
            name = "subnet_generator",
            sequenceName = "subnet_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String text;

    
    @NotBlank
    @ApiModelProperty(notes="Type should be VM or ELB or ALB")
	private String type;
    
   
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToOne (fetch = FetchType.EAGER, /*cascade=CascadeType.ALL,*/ optional = true)
	@JoinColumn(name = "subnetcidr_id", nullable = true)
	//@JsonManagedReference
	private SubnetCidr sCidr;

	public SubnetCidr getsCidr() {
		return sCidr;
	}

	public void setsCidr(SubnetCidr sCidr) {
		this.sCidr = sCidr;
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
    @JoinColumn(name = "az_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Az az;

	public Az getAz() {
		return az;
	}

	public void setAz(Az az) {
		this.az = az;
	}
	
	/**/
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "subnet_subnetgroup",
    joinColumns = { @JoinColumn(name = "subnet_id") },
    inverseJoinColumns = { @JoinColumn(name = "subnetgroup_id") })
	@JsonIgnoreProperties({ "subnets"})
    private List<SubnetGroup> subnetgroup = new ArrayList<>();



	public List<SubnetGroup> getSubnetgroup() {
		return subnetgroup;
	}

	public void setSubnetgroup(List<SubnetGroup> subnetgroup) {
		this.subnetgroup = subnetgroup;
	}


	
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "subnet_nacl", 
    joinColumns = { @JoinColumn(name = "subnet_id", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "nacl_id", nullable = false, updatable = false) })
	@JsonIgnoreProperties({ "subnets"})
	
    private List<Nacl> nacls = new ArrayList<>();
    
	public List<Nacl> getNacls() {
		return nacls;
	}

	public void setNacls(List<Nacl> nacls) {
		this.nacls = nacls;
	}
    

	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "subnet_routetable", 
    joinColumns = { @JoinColumn(name = "subnet_id", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "routetable_id", nullable = false, updatable = false) })
	@JsonIgnoreProperties({ "subnets"})
	
    private List<RouteTable> routetables = new ArrayList<>();

	public List<RouteTable> getRoutetables() {
		return routetables;
	}

	public void setRoutetables(List<RouteTable> routetables) {
		this.routetables = routetables;
	}
	
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "subnet_lb", 
    joinColumns = { @JoinColumn(name = "subnet_id", nullable = false, updatable = false) },
    inverseJoinColumns = { @JoinColumn(name = "lb_id", nullable = false, updatable = false) })
	@JsonIgnoreProperties({ "subnets"})
    private List<Lb> lbs = new ArrayList<>();



	public List<Lb> getLbs() {
		return lbs;
	}

	public void setLbs(List<Lb> lbs) {
		this.lbs = lbs;
	}

}
