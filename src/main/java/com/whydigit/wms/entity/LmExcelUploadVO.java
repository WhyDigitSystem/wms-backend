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
	
	@Column(name = "fromlocation")
	private String fromLocation;

	@Column(name = "fromlocationtype")
	private String fromLocationType;

	@Column(name = "locationpick")
	private String locationPick;

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

}
