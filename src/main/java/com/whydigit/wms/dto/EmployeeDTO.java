package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

	private Long id;

	private String employeeCode;
	private String employeeName;
	private String gender;
	private String branch;
	private String branchCode;
	private String department;
	private String designation;
	private String dateOfBirth;
	private String joiningdate;
	private String createdBy;
	private Long orgId;
	private boolean cancel;
	private String cancelRemark;
	private boolean active;
}
