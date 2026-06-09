package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
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

	@Column(name = "carrier",length =150)
	private String carrier;
	@Column(name = "carriershortname",length =150)
	private String carrierShortName;
	@Column(name = "shipmentmode",length =25)
	private String shipmentMode;
	@Column(name = "cbranch",length =10)
	private String cbranch;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =10)
	private String branchCode;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;

//	@JsonManagedReference
//	@OneToMany(mappedBy = "carrierVO", cascade = CascadeType.ALL)
//	private List<CarrierDetailsVO> carrierDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
