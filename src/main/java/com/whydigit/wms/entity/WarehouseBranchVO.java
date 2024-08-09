package com.whydigit.wms.entity;

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
@Table(name = "warehousebranch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseBranchVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehousebranchgen")
	@SequenceGenerator(name = "warehousebranchgen", sequenceName = "warehousebranchseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehousebranchid")
	private Long id;
	@Column(name ="customerbranchcode")
	private String customerBranchCode;
	@Column(name = "active")
	private boolean active;
	private boolean cancel;
	@Column(name ="orgid")
	private Long orgId;
	
	

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "warehouseid")
//	private WarehouseVO warehouseVO;
}
