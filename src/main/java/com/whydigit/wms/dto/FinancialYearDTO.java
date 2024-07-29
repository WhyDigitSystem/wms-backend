package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearDTO {
	
	private Long id;
	private int finYear;
	private int finYearIdentifier;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean currentFinYear;
	private boolean closed;
	private String orgId;
	private String createdBy;
	private boolean active;

}
