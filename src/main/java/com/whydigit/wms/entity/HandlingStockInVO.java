package com.whydigit.wms.entity;

import java.time.LocalDate;

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
@Table(name = "handlingstockin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handlingstockingen")
	@SequenceGenerator(name = "handlingstockingen", sequenceName = "handlingstockinVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockinid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno", length = 30)
	private String refno;
	@Column(name = "grnno", length = 30)
	private String grnno;
	@Column(name = "refdate", length = 30)
	private LocalDate refdate;
	@Column(name = "partno", length = 30)
	private String partno;
	@Column(name = "grndate", length = 30)
	private LocalDate grndate;
	@Column(name = "partdesc", length = 30)
	private String partdesc;
	@Column(name = "rpqty")
	private int rpqty;
	@Column(name = "noofpallet", length = 30)
	private String noofpallet;
	@Column(name = "locationtype", length = 30)
	private String locationtype;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "ssqty")
	private int ssqty;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;
	@Column(name = "damageqty")
	private int damageqty;
	@Column(name = "shortqty")
	private int shortqty;
	@Column(name = "palletqty")
	private int palletqty;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount", length = 30)
	private String amount;
	@Column(name = "qcflag", length = 30)
	private String qcflag;
	@Column(name = "remarks", length = 30)
	private String remarks;
	@Column(name = "finyr", length = 30)
	private String finyr;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchid", length = 30)
	private String branchid;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "client", length = 30)
	private String client;
	@Column(name = "palletcount", length = 30)
	private String palletcount;
	@Column(name = "indcno", length = 30)
	private String indcno;
	@Column(name = "lrhawbhblno", length = 30)
	private String lrhawbhblno;
	@Column(name = "sku", length = 30)
	private String sku;
	@Column(name = "sdocid", length = 30)
	private String sdocid;
	@Column(name = "stockdate", length = 30)
	private LocalDate stockdate;
	@Column(name = "sdocdate", length = 30)
	private LocalDate sdocdate;
	@Column(name = "sourcescreen", length = 30)
	private String sourcescreen;
	@Column(name = "expdate", length = 30)
	private String expdate;
	@Column(name = "batchno", length = 30)
	private String batchno;
	@Column(name = "batchdt", length = 30)
	private String batchdt;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "modifiedby", length = 30)
	private String createdby;
	@Column(name = "updatedby", length = 30)
	private String updatedby;
	@Column(name = "company", length = 30)
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "cancelremark")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "ssku", length = 30)
	private String ssku;
	@Column(name = "screencode", length = 30)
	private String screencode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
