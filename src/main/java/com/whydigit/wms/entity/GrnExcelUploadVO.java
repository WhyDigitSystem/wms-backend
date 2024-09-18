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

    @Column(name = "entryno")
    private String entryNo;

    @Column(name = "entrydate")
    private LocalDate entryDate;  // Use String or LocalDate depending on your format

    @Column(name = "suppliershortname")
    private String supplierShortname;

    @Column(name = "modeofshipment")
    private String modeOfShipment;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "lrhblno")
    private String lrHblNo;

    @Column(name = "invdcno")
    private String invDcNo;

    @Column(name = "invdate")
    private LocalDate invDate;

    @Column(name = "partno")
    private String partNo;

    @Column(name = "partdesc")
    private String partDesc;

    @Column(name = "sku")
    private String sku;

    @Column(name = "invqty")
    private Integer invQty;

    @Column(name = "recqty")
    private Integer recQty;

    @Column(name = "damageqty")
    private Integer damageQty;

    @Column(name = "substockqty")
    private Integer subStockQty;

    @Column(name = "batchno")
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

    @Column(name = "remark")
    private String remark;
    
    @Column(name = "orgid")
    private Long orgId;

    
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
    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

    // Add other common fields if needed
}
