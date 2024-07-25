package com.whydigit.wms.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierDTO {
	
	private Long id;
	private String carrier;
	private String carrierShortName;
	private String shipmentMode;
	private String cbranch;
	private String client;
	private Long orgId;
	private boolean active;
	private String customer;
	private String warehouse;
	private String branch;
	private String branchCode;
	private String createdBy;

}
