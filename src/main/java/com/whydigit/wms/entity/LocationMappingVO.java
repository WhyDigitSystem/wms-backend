package com.whydigit.wms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationmappinggen")
	@SequenceGenerator(name = "locationmappinggen", sequenceName = "locationmappingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "locationmappingid")
	private Long id;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "clienttype",length =25)
	private String clientType;
	@Column(name = "rowno",length =25)
	private String rowNo;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "levelno",length =25)
	private String levelNo;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	private boolean cancel;
	@Column(name = "cancelremark",length =150)
	private String cancelRemark;
	private boolean active;
	@Column(name = "branchcode",length =25)
	private String branchCode;

	@JsonManagedReference
	@OneToMany(mappedBy = "locationMappingVO", cascade = CascadeType.ALL)
	private List<LocationMappingDetailsVO> locationMappingDetails;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
