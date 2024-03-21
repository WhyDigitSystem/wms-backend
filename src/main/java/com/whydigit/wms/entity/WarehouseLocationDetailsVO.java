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

	@Column(name = "branchname")
	private String branchname;

	@Column(name = "branchcode")
	private String branchcode;

	@Column(name = "warehouse")
	private String warehouse;

	@Column(name = "locatintype")
	private String locationtype;

	@Column(name = "rowno")
	private String rowno;

	@Column(name = "level")
	private String level;

	@Column(name = "cellfrom")
	private String cellfrom;

	@Column(name = "cellto")
	private String cellto;

	@Column(name = "bin")
	private String bin;

	@Column(name = "bincategory")
	private String bincategory;

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
