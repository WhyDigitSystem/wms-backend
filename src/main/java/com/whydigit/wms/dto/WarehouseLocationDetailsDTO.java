package com.whydigit.wms.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationDetailsDTO {
	
	private Long id;
	@Column(name = "bin")
	private String bin;
	@Column(name = "bincategory")
	private String binCategory;
	@Column(name = "status")
	private String status;
	@Column(name = "core")
	private String core;

}
