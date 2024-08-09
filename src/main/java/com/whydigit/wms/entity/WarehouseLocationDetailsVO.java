package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouselocationdetails")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseLocationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouselocationdetailsgen")
	@SequenceGenerator(name = "warehouselocationdetailsgen", sequenceName = "warehouselocationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouselocationdetailsid")
	private Long id;

	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "rowno")
	private String rowNo;
	@Column(name = "level")
	private String level;
	@Column(name = "cellfrom")
	private String cellFrom;
	@Column(name = "cellto")
	private String cellTo;
	@Column(name = "bin")
	private String bin;
	@Column(name = "bincategory")
	private String binCategory;
	@Column(name = "status")
	private String status;
	@Column(name = "core")
	private String core;
	@Column(name = "orgid")
	private Long orgId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "warehouselocationid")
	private WarehouseLocationVO warehouseLocationVO;

}
