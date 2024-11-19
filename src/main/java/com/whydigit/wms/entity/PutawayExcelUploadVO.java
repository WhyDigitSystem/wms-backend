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
    
    
    @Column(name = "grnno",length =25)
    private String grnNo;

    @Column(name = "grndate")
    private LocalDate grnDate; 

    @Column(name = "entryno",length =25)
    private String entryNo;

    @Column(name = "entrydate")
    private LocalDate entryDate; 
    
    @Column(name = "shortname",length =25)
    private String shortName;

    @Column(name = "modeofshipment",length =25)
    private String modeOfShipment;

    @Column(name = "carrier",length =150)
    private String carrier;

    @Column(name = "type",length =25)
    private String type;

    @Column(name = "core",length =25)
    private String core;

    @Column(name = "binpick",length =25)
    private String binPick;

    @Column(name = "lrhawbhblno",length =25)
    private String lrHawbhblNo;

    @Column(name = "indcno",length =25)
    private String indcNo;

    @Column(name = "bintype",length =25)
    private String binType;

    @Column(name = "partno",length =25)
    private String partNo;

    @Column(name = "batchno",length =25)
    private String batchNo;

    @Column(name = "partdesc",length =150)
    private String partDesc;

    @Column(name = "sku",length =25)
    private String sku;

    @Column(name = "ssku",length =25)
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

    @Column(name = "binno",length =25)
    private String binNo;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "remarks",length =25)
    private String remarks;

    @Column(name = "vehicletype",length =25)
    private String vehicleType;

    @Column(name = "vehicleno",length =25)
    private String vehicleNo;

    @Column(name = "drivername",length =25)
    private String driverName;

    @Column(name = "contact",length =25)
    private String contact;

    @Column(name = "goodsdesc",length =150)
    private String goodsDesc;

    @Column(name = "securitypersonname",length =25)
    private String securityPersonName;
    
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
}

