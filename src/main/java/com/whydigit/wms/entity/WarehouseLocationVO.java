package com.whydigit.wms.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="warehouselocation")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseLocationVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "warehouselocationgen")
	@SequenceGenerator(name = "warehouselocationgen",sequenceName = "warehouselocationVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="warehouselocationid")
	private Long id;
	private String branch;
	private String branchcode;
	private String warehouse;
	private String locationtype;
	private String rowno;
	private String level;
	private String cellfrom;
	private String cellto;
	private boolean cancel;
	private boolean active;
	private String createdon;
	private String modifiedon;
	private String userid;
	private Long orgid;
	
	@OneToMany(mappedBy = "warehouseLocationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseLocationDetailsVO>warehouseLocationDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
