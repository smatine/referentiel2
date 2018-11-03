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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "targets")
public class Target extends AuditModel {
    @Id
    @GeneratedValue(generator = "target_generator")
    @SequenceGenerator(
            name = "target_generator",
            sequenceName = "target_sequence",
            initialValue = 1000
    )
    private Long id;
    
    
    private Long port;
    
    @NotBlank
    private String ec2;
    
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "targetgroup_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TargetGroup targetGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public String getEc2() {
		return ec2;
	}

	public void setEc2(String ec2) {
		this.ec2 = ec2;
	}

	public TargetGroup getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(TargetGroup targetGroup) {
		this.targetGroup = targetGroup;
	}
        
    
    
}
