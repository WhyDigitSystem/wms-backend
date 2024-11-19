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
@Table(name = "grnexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnExcelUploadVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grnexceluploadgen")
    @SequenceGenerator(name = "grnexceluploadgen", sequenceName = "grnexceluploadseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "grnexceluploadid")
    private Long id;

    @Column(name = "sno")
    private Integer sno;

    @Column(name = "entryno",length =25)
    private String entryNo;

    @Column(name = "entrydate")
    private LocalDate entryDate;  // Use String or LocalDate depending on your format

    @Column(name = "suppliershortname",length =150)
    private String supplierShortname;

    @Column(name = "modeofshipment",length =25)
    private String modeOfShipment;

    @Column(name = "carrier",length =150)
    private String carrier;

    @Column(name = "lrhblno",length =25)
    private String lrHblNo;

    @Column(name = "invdcno",length =25)
    private String invDcNo;

    @Column(name = "invdate")
    private LocalDate invDate;

    @Column(name = "partno",length =25)
    private String partNo;

    @Column(name = "partdesc",length =150)
    private String partDesc;

    @Column(name = "sku",length =25)
    private String sku;

    @Column(name = "invqty")
    private Integer invQty;

    @Column(name = "recqty")
    private Integer recQty;

    @Column(name = "damageqty")
    private Integer damageQty;

    @Column(name = "substockqty")
    private Integer subStockQty;

    @Column(name = "batchno",length =25)
    private String batchNo;

    @Column(name = "batchdate")
    private LocalDate batchDate;

    @Column(name = "expdate")
    private LocalDate expDate;

    @Column(name = "noofpallet")
    private Integer noOfPallet;

    @Column(name = "palletqty")
    private Integer palletQty;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "remark",length =150)
    private String remark;
    
    @Column(name = "orgid")
    private Long orgId;

    
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
    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

    // Add other common fields if needed
}
