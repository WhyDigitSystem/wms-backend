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
@Table(name = "warehouselocation")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseLocationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouselocationgen")
	@SequenceGenerator(name = "warehouselocationgen", sequenceName = "warehouselocationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouselocationid")
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
	@Column(name = "cellform")
	private String cellFrom;
	@Column(name = "cellto")
	private String cellTo;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@OneToMany(mappedBy = "warehouseLocationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseLocationDetailsVO> warehouseLocationDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
