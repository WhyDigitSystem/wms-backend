package com.whydigit.wms.entity;

import java.time.LocalDate;

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
@Table(name = "handlingStockaOut")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockaOutVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String orgId;
	private String refno;
	private String partno;
	private LocalDate refdate;
	private String partdesc;
	private String rpqty;
	private String customer;
	private String warehouse;
	private String sqty;
	private String pickrequestno;
	private LocalDate pickrequestdate;
	private String buyerorderno;
	private String sku;
	private LocalDate buyerorderdate;
	private String pickqty;
	private String buyerordno;
	private String sdocid;
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
	private String branch;
	private String screencode;
	private String client;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}