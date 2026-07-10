package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeDTO {
	
	private Long id;
	private String screenCode;
	private String screenName;
	private String description;
	private String docCode;
	private String createdBy;
	private Long orgId;	
//	private List<DocumentTypeDetailsDTO> documentTypeDetailsDTO;
//	

}
