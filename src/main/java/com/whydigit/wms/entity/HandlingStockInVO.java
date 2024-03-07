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
@Table(name = "handlingstockin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String orgId;
	private String refno;
	private String grnno;
	private LocalDate refdate;
	private String partno;
	private LocalDate grndate;
	private String partdesc;
	private String rpqty;
	private String noofpallet;
	private String locationtype;
	private String sqty;
	private String ssqty;
	private String invqty;
	private String recqty;
	private String damageqty;
	private String shortqty;
	private String palletqty;
	private String rate;
	private String amount;
	private String qcflag;
	private String remarks;
	private String finyr;
	private String branch;
	private String branchid;
	private String branchcode;
	private String customer;
	private String warehouse;
	private String client;
	private String palletcount;
	private String indcno;
	private String lrhawbhblno;
	private String sku;
	private String sdocid;
	private LocalDate stockdate;
	private String sourcscreen;
	private String expdate;
	private String batchno;
	private String batchdt;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String screencode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}