package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class WarehouseClientDTO {
	private Long id;
	private String client;
	private String clientCode;
	private boolean active;
}
