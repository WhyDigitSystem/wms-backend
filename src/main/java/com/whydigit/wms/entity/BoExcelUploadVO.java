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
@Table(name = "boexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoExcelUploadVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bogen")
	@SequenceGenerator(name = "bogen", sequenceName = "boseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "excelboid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;
	
	
	@Column(name = "type",length =25)
	private String type;

	@Column(name = "orderno",length =25)
	private String orderNo;

	@Column(name = "orderdate")
	private LocalDate orderDate;

	@Column(name = "invoiceno",length =25)
	private String invoiceNo;

	@Column(name = "invoicedate")
	private LocalDate invoiceDate;

	@Column(name = "referenceno",length =25)
	private String referenceNo;

	@Column(name = "referencedate")
	private LocalDate referenceDate;

	@Column(name = "buyername",length =150)
	private String buyerName;

	@Column(name = "billto",length =150)
	private String billTo;

	@Column(name = "shipto",length =150)
	private String shipTo;

	@Column(name = "partno",length =25)
	private String partNo;

	@Column(name = "partdesc",length =150)
	private String partDesc;

	@Column(name = "batchno",length =25)
	private String batchNo;

	@Column(name = "sku",length =25)
	private String sku;

	@Column(name = "qty")
	private Integer qty;

	@Column(name = "unitrate")
	private Double unitRate;

	@Column(name = "remark",length =150)
	private String remark;
	
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "finyear",length =5)
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
	
}
