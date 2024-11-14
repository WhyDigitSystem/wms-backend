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

	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "rowno",length =25)
	private String rowNo;
	@Column(name = "level",length =25)
	private String level;
	@Column(name = "cellfrom",length =25)
	private String cellFrom;
	@Column(name = "cellto",length =25)
	private String cellTo;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "bincategory",length =25)
	private String binCategory;
	@Column(name = "status",length =10)
	private String status;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "orgid")
	private Long orgId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "warehouselocationid")
	private WarehouseLocationVO warehouseLocationVO;

}
