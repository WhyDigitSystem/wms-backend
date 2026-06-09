package com.whydigit.wms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRestateDTO {

	private Long id;
	private String transferFrom;
	private String transferTo;
	private String transferFromFlag;
	private String transferToFlag;
	private String entryNo;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	List<StockRestateDetailsDTO> stockRestateDetailsDTO;
}
