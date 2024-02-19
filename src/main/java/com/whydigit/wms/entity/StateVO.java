package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stateid;
	private String statecode;
	private String statename;
    private String country;
    private String region;
    private boolean active;
    private String userid;
    private String company;
	@Column(unique = true)
	private String dupchk;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
