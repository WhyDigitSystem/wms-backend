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
@Table(name = "locationmapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMappingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long locationmappingid;
	private String branch;
	private String warehouse;
	private String locationtype;
	private String clienttype;
	private String rowno;
	private String levelno;
	private String client;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String branchcode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
