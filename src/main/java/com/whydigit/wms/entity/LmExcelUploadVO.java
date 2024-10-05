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
@Table(name = "lmexcelupload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LmExcelUploadVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lmgen")
	@SequenceGenerator(name = "lmgen", sequenceName = "lmseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "excellmid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "type",length =25)
	private String type;
	
	@Column(name = "frombin",length =25)
	private String frombin;

	@Column(name = "frombintype",length =25)
	private String fromBinType;

	@Column(name = "binpick",length =25)
	private String binPick;

	@Column(name = "partno",length =25)
	private String partNo;

	@Column(name = "partdesc",length =150)
	private String partDesc;

	@Column(name = "sku",length =25)
	private String sku;

	@Column(name = "grnno",length =25)
	private String grnNo;

	@Column(name = "grndate")
	private LocalDate grnDate;

	@Column(name = "batchno",length =25)
	private String batchNo;

	@Column(name = "expdate")
	private LocalDate expDate;

	@Column(name = "entryno",length =25)
	private String entryNo;
	
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
