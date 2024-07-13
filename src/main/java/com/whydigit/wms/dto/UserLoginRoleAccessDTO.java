package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRoleAccessDTO {
	
	private String role;
	private LocalDate startdate;
	private LocalDate enddate;
	


}
