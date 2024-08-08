package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyerOrderDTO {
	private Long id;

	private String orderNo;
	private LocalDate docDate;
	private LocalDate orderDate;
	private String invoiceNo;
	private String refNo;
	private LocalDate invoiceDate;
	private LocalDate refDate;
	private String buyerShortName;
	private String currency;
	private String exRate;
	private String location;
	private String billto;
	private String tax;
	private String shipTo;
	private String reMarks;
	private String createdBy;
	private String updatedBy;
	private String company;
	private boolean cancel;
	private String cancelRemark;
	private String screenCode;
	
	private List<BuyerOrderDetailsDTO> buyerOrderDetailsDTO;
	
}
