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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long warehouseid;
	private String warehousename;
	private String branchcode;
	private String company;
	@Column(unique = true)
	private String dupchk;
	private boolean active;
	private String userid;
	private String createdby;
	private String updatedby;
	private boolean cancel;
	

	@OneToMany(mappedBy = "warehouseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseClientVO> warehouseClientVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
