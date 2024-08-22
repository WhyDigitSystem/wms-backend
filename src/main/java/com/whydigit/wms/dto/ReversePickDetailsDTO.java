package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReversePickDetailsDTO {

	private Long id;

	private String partCode;

	private String partDesc;

	private String batchNo;

	private String lotNo;

	private String sku;

	private String location;

	private String toLocation;

	private int orderQty;

	private int pickedQtyPerLocation;

	private int revisedQtyPerLocation;

	private int weight;

	private int pGroup;

	private LocalDate expDate;

	private int rate;

	private int tax;

	private int amount;

	private String remarks;

}
