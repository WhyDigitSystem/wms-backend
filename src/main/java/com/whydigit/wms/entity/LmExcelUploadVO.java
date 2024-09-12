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

	@Column(name = "type")
	private String type;
	
	@Column(name = "frombin")
	private String frombin;

	@Column(name = "frombintype")
	private String fromBinType;

	@Column(name = "binpick")
	private String binPick;

	@Column(name = "partno")
	private String partNo;

	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "sku")
	private String sku;

	@Column(name = "grnno")
	private String grnNo;

	@Column(name = "grndate")
	private LocalDate grnDate;

	@Column(name = "batchno")
	private String batchNo;

	@Column(name = "expdate")
	private LocalDate expDate;

	@Column(name = "entryno")
	private String entryNo;
	
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
