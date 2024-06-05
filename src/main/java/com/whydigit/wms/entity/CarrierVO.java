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
	@SequenceGenerator(name = "carriergen", sequenceName = "carrierVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "carrierid")
	private Long id;

	@Column(name = "carrier", length = 30)
	private String carrier;
	@Column(name = "carriershortname", length = 30)
	private String carriershortname;
	@Column(name = "shipmentmode", length = 30)
	private String shipmentmode;
	@Column(name = "cbranch", length = 30)
	private String cbranch;
	@Column(name = "client", length = 30)
	private String client;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks", length = 30)
	private String cancelremarks;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;

//	@JsonManagedReference
//	@OneToMany(mappedBy = "carrierVO", cascade = CascadeType.ALL)
//	private List<CarrierDetailsVO> carrierDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
