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
	private String branch;
	private String warehouse;
	@Column(name ="bintype")
	private String binType;
	@Column(name ="clienttype")
	private String clientType;
	@Column(name ="rowno")
	private String rowNo;
	private String customer;
	@Column(name ="levelno" )
	private String levelNo;
	private String client;
	@Column(name ="createdby" )
	private String createdBy;
	@Column(name ="modifiedby")
	private String updatedBy;
    @Column(name ="orgid")
	private Long orgId;
	private boolean cancel;
	@Column(name ="cancelremark")
	private String cancelRemark;
	private boolean active;
	@Column(name ="branchcode")
	private String branchCode;

	@JsonManagedReference
	@OneToMany(mappedBy = "locationMappingVO", cascade = CascadeType.ALL)
	private List<LocationMappingDetailsVO>locationMappingDetails;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
