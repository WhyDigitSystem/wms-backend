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

	@Column(name = "orgId")
	private Long orgId;
	
	
	@Column(name = "type")
	private String type;

	@Column(name = "orderno")
	private Integer orderNo;

	@Column(name = "orderdate")
	private LocalDate orderDate;

	@Column(name = "invoiceno")
	private String invoiceNo;

	@Column(name = "invoicedate")
	private LocalDate invoiceDate;

	@Column(name = "referenceno")
	private String referenceNo;

	@Column(name = "referencedate")
	private LocalDate referenceDate;

	@Column(name = "buyername")
	private String buyerName;

	@Column(name = "billto")
	private String billTo;

	@Column(name = "shipto")
	private String shipTo;

	@Column(name = "partno")
	private String partNo;

	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "batchno")
	private String batchNo;

	@Column(name = "sku")
	private String sku;

	@Column(name = "qty")
	private Integer qty;

	@Column(name = "unitrate")
	private Double unitRate;

	@Column(name = "remark")
	private String remark;

}
