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
@Table(name = "warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "warehousegen")
	@SequenceGenerator(name = "warehousegen",sequenceName = "warehouseVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="warehouseid")
	private Long id;
	private String warehouse;
	private String branchcode;
	private String branch;
	private Long orgid;
	@Column(unique = true)
	private String dupchk;
	private boolean active;
	private String userid;
	private String createdby;
	private String modifiedby;
	private boolean cancel;
	

	@OneToMany(mappedBy = "warehouseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseClientVO> warehouseClientVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
