package com.whydigit.wms.entity;

import javax.persistence.Column;
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
	@Column(name="branch")
	private String branch;
	@Column(name="branchcode")
	private String branchCode;
	@Column(name="warehouse")
	private String warehouse;
	@Column(name="bintype")
	private String binType;
	@Column(name="clienttype")
	private String clientType;
	@Column(name="rowno")
	private String rowNo;
	@Column(name="levelno")
	private String levelNo;
	@Column(name="client")
	private String client;
	private boolean cancel;
	@Column(name="bin")
	private String bin;
	@Column(name="lstatus")
	private String lstatus;
	@Column(name="cellcategory")
	private String cellCategory;
	@Column(name="core")
	private String core;
	private boolean active;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "locationmappingid")
	private LocationMappingVO locationMappingVO;

}
