package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {

	private String username;
    private String password;
    private String employeename;
    private String nickname;
    private String email;
    private String mobileno;
    private String usertype;
    private String active;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}}
//    private List<UserLoginRoleAccessDTO>roleAccessDTO;
//    private List<UserLoginClientAccessDTO> clientAccessDTOList;
//    private List<UserLoginBranchAccessDTO> branchAccessDTOList;
 