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
	@SequenceGenerator(name = "warehouselocationdetailsgen", sequenceName = "warehouselocationdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouselocationdetailsid")
	private Long id;

  @Column(name = "branch", length = 30)
	private String branch;

  @Column(name = "branchcode", length = 30)
	private String branchcode;

	@Column(name = "warehouse", length = 30)
	private String warehouse;

	@Column(name = "locatintype", length = 30)
	private String locationtype;

	@Column(name = "rowno")
	private String rowno;

	@Column(name = "level", length = 30)
	private String level;

	@Column(name = "cellfrom", length = 30)
	private String cellfrom;

	@Column(name = "cellto", length = 30)
	private String cellto;

	@Column(name = "bin", length = 30)
	private String bin;

	@Column(name = "bincategory", length = 30)
	private String bincategory;

	@Column(name = "status", length = 30)
	private String status;

	@Column(name = "core", length = 30)
	private String core;

	@Column(name = "orgid")
	private Long orgId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "warehouselocationid")
	private WarehouseLocationVO warehouseLocationVO;

}
