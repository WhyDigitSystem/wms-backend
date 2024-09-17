package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pcexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutawayExcelUploadVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pcexceluploadgen")
    @SequenceGenerator(name = "pcexceluploadgen", sequenceName = "pcexceluploadseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "pcexceluploadid")
    private Long id;

    @Column(name = "orgid")
    private Long orgId;
    
    @Column(name = "grnno")
    private String grnNo;

    @Column(name = "grndate")
    private LocalDate grnDate; 

    @Column(name = "entryno")
    private String entryNo;

    @Column(name = "entrydate")
    private LocalDate entryDate; 
    
    @Column(name = "shortname")
    private String shortName;

    @Column(name = "modeofshipment")
    private String modeOfShipment;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "type")
    private String type;

    @Column(name = "core")
    private String core;

    @Column(name = "binpick")
    private String binPick;

    @Column(name = "lrhawbhblno")
    private String lrHawbhblNo;

    @Column(name = "indcno")
    private String indcNo;

    @Column(name = "bintype")
    private String binType;

    @Column(name = "partno")
    private String partNo;

    @Column(name = "batchno")
    private String batchNo;

    @Column(name = "partdesc")
    private String partDesc;

    @Column(name = "sku")
    private String sku;

    @Column(name = "ssku")
    private String ssku;

    @Column(name = "invqty")
    private Integer invQty;

    @Column(name = "recqty")
    private Integer recQty;

    @Column(name = "shortqty")
    private Integer shortQty;

    @Column(name = "damageqty")
    private Integer damageQty;

    @Column(name = "grnqty")
    private Integer grnQty;

    @Column(name = "sqty")
    private Integer sqty;

    @Column(name = "ssqty")
    private Integer ssQty;

    @Column(name = "sssqty")
    private Integer sssQty;

    @Column(name = "binqty")
    private Integer binQty;

    @Column(name = "binno")
    private String binNo;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "vehicletype")
    private String vehicleType;

    @Column(name = "vehicleno")
    private String vehicleNo;

    @Column(name = "drivername")
    private String driverName;

    @Column(name = "contact")
    private String contact;

    @Column(name = "goodsdesc")
    private String goodsDesc;

    @Column(name = "securitypersonname")
    private String securityPersonName;
    
	@Column(name = "customer")
	private String customer;
	@Column(name = "client")
	private String client;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
}

