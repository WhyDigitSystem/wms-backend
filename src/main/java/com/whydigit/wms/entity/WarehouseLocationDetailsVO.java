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
@Table(name="warehouselocationdetails")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseLocationDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "warehouselocationdetailsgen")
	@SequenceGenerator(name = "warehouselocationdetailsgen",sequenceName = "warehouselocationdetailsVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="warehouselocationdetailsid")
	private Long id;
	private String branchname;
	private String branchcode;
	private String warehouse;
	private String locationtype;
	private String rowno;
	private String level;
	private String cellfrom;
	private String cellto;
	private String bin;
	private String bincategory;
	private String status;
	private String core;
	private Long orgid;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="warehouselocationid")
	private WarehouseLocationVO warehouseLocationVO;
	
	
	
}
