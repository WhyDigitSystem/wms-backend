package com.whydigit.wms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locationmappingdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMappingDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationmappingdetailsgen")
	@SequenceGenerator(name = "locationmappingdetailsgen", sequenceName = "locationmappingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	private Long id;
	private String branch;
	private String warehouse;
	private String locationtype;
	private String clienttype;
	private String rowno;
	private String levelno;
	private String client;
	private Long orgId;
	private boolean cancel;
	private String bin;
	private String lstatus;
	private String cellcategory;
	private String code;
	private boolean active;
	private String branchcode;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "locationmappingid")
	private LocationMappingVO locationMappingVO;

}
