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
	@SequenceGenerator(name = "warehouselocationgen", sequenceName = "warehouselocationVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouselocationid")
	private Long id;

	@Column(name = "branch", length = 30)
	private String branch;

	@Column(name = "branchcode", length = 30)
	private String branchcode;

	@Column(name = "warehouse", length = 30)
	private String warehouse;

	@Column(name = "locationtype", length = 30)
	private String locationtype;

	@Column(name = "rowno", length = 30)
	private String rowno;

	@Column(name = "level", length = 30)
	private String level;

	@Column(name = "cellform", length = 30)
	private String cellfrom;

	@Column(name = "cellto", length = 30)
	private String cellto;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "active")
	private boolean active;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "orgid")
	private Long orgId;

	@Column(unique = true)
	private String dupchk;
	
	@OneToMany(mappedBy = "warehouseLocationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseLocationDetailsVO> warehouseLocationDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
