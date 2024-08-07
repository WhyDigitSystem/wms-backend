package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMappingDetailsDTO {
	private Long id;
	private String pallet;
	private String partNo;
	private String partDescripition;
	private String GRNNo;
	private String batchNo;
	private LocalDate batchDate;
	private String lotNo;
	private String toLocation;
	private String fromLocation;
	private int fromQty;
	private int toQty;
	private int remainingQty;
}
