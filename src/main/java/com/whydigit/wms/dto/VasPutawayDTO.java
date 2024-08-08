package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VasPutawayDTO {
	private Long id;
	private String docId;
	private LocalDate docDate = LocalDate.now();
	private String vasPickNo;
	private String status;

	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	
	List<VasPutawayDetailsDTO> vasPutawayDetailsDTO;
}
