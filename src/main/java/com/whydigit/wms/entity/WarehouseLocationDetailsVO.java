package com.whydigit.wms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long warehouselocationdetailsid;
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
	private String company;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="warehouselocationid")
	private WarehouseLocationVO warehouseLocationVO;
	
	
	
}
