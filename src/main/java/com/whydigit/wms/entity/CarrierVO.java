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
@Table(name = "carrier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String carrier;
	private String carriershortname;
	private String shipmentmode;
	private String cbranch;
	private String client;
	@Column(unique = true)
	private String dupchk;
	private Long orgId;
	private boolean active;
	private String userid;
	private String customer;
	private String warehouse;
	private String branch;
	private String branchcode;
	private boolean cancel;
	private String cancelremarks;
	private String createdby;
	private String updatedby;
	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "carrierVO", cascade = CascadeType.ALL)
//	private List<CarrierDetailsVO> carrierDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
