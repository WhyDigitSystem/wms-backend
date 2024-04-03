package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDTO {
	private String direct;
	private String docid;
	private LocalDate docdate;
	private String entryno;
	private LocalDate entrydate;
	private LocalDate grndate;
	private boolean vas;
	private String gatepassid;
	private LocalDate gatepassdate;
	private String customerpo;
	private String suppliershortname;
	private String supplier;
	private String carrier;
	private String lotno;
	private String modeofshipment;
	private Long orgId;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String branchcode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;
	private String finyr;
	private String noofpackages;
	private String totalamount;
	private int totalgrnqty;//
	private String createdby;//both
	List<GrnDetailsDTO> grnDetailsDTO;

}
