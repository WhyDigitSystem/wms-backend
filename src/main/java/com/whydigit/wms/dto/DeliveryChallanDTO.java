package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanDTO {

    private Long id;
	private String buyerOrderNo;
	private LocalDate pickReqDate;
	private String invoiceNo;
	private String containerNO;
	private String vechileNo;
	private String exciseInvoiceNo;
	private String commercialInvoiceNo;
	private LocalDate boDate;
	private String buyer;
	private String deliveryTerms;
	private String payTerms;
	private String grWaiverNo;
	private LocalDate grWaiverDate;
	private String bankName;
	private LocalDate grWaiverClosureDate;
	private String gatePassNo;
	private LocalDate gatePassDate;
	private String insuranceNo;
	private String billTo;
	private String shipTo;
	private String automailerGroup;
	private String docketNo;
	private String noOfBoxes;
	private String pkgUom;
	private String grossWeight;
	private String gwtUom;
	private String transportName;
	private LocalDate transporterDate;
	private String packingSlipNo;
	private String bin;
	private String taxType;
	private String remarks;
	

	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	
	List<DeliveryChallanDetailsDTO> DeliveryChallanDetailsDTO;
}
