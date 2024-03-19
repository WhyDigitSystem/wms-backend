package com.whydigit.wms.entity;

import java.time.LocalDate;

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
@Table(name = "stockdetils")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cancel;
	private String orgId;
	private String createdby;
	private String cancelremarks;
	private String branchcode;
	private String docid;
	private LocalDate docdate;
	private String qtypicked;
	private String rate;
	private String amount;
	private String inouttime;
	private String refno;
	private LocalDate refdate;
	private int qty;
	private String lrhawbhblno;
	private String client;
	private LocalDate stockdate;
	private String grnno;
	private LocalDate grndate;
	private String carrier;
	private int oqty;
	private int rqty;
	private int dqty;
	private int cqty;
	private int uqty;
	private String branch;
	private String partno;
	private String partdesc;
	private String sourcescreen;
	private String remarks;
	private String customer;
	private String locationtype;
	private String celltype;
	private String core;
	private String toqty;
	private String pallet;
	private String warehouse;
	private String sku;
	private int ssku;
	private int ssqty;
	private int sqty;
	private int pqty;
	private int invqty;
	private int recqty;
	private int damageqty;
	private int shortqty;
	private String qcflag;
	private String subtypecode;
	private String pamount;
	private String locationclass;
	private String pgroup;
	private String barcode;
	private String stylecode;
	private String expdate;
	private String buyerorderno;
	private String batchno;
	private String batchdt;
	private String weight;
	private String pckey;
	private String sdactual;
	private String tpartno;
	private String taskid;
	private String sdate;
	private String cdocdate;
	private String status;
	private String invoiceno;
	private String istatus;
	private String sflag;
	private String lotno;
	private String company;
	private String userid;
	private boolean active;
	private String branchname;
	private String updatedby;
	private String cancelremark;
	private String screencode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}