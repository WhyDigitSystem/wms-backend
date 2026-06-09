package com.whydigit.wms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMappingDTO {
	
	
	private String branch;
	
	private String branchCode;
	
	private String finYear;
	
	private String finYearIdentifier;
	
	private Long orgId;
	
	private String createdBy;
	
	private List<DocumentTypeMappingDetailsDTO> documentTypeMappingDetailsDTO;
	

}
