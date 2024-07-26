package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carriergen")
	@SequenceGenerator(name = "carriergen", sequenceName = "carrierseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "carrierid")
	private Long id;

	@Column(name = "carrier")
	private String carrier;
	@Column(name = "carriershortname")
	private String carrierShortName;
	@Column(name = "shipmentmode")
	private String shipmentMode;
	@Column(name = "cbranch")
	private String cbranch;
	@Column(name = "client")
	private String client;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;

//	@JsonManagedReference
//	@OneToMany(mappedBy = "carrierVO", cascade = CascadeType.ALL)
//	private List<CarrierDetailsVO> carrierDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
