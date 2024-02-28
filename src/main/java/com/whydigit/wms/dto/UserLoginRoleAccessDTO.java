package com.whydigit.wms.dto;

import java.time.LocalDate;

public class UserLoginRoleAccessDTO {
	
	private String role;
	private LocalDate startdate;
	private LocalDate enddate;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	
	
	

}
