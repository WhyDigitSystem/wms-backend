package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {

	private String userName;
    private String password;
    private String employeeName;
    private String nickName;
    private String email;
    private String mobileNo;
    private String userType;
    private String isActive;
//    private List<UserLoginRoleAccessDTO>roleAccessDTO;
//    private List<UserLoginClientAccessDTO> clientAccessDTOList;
//    private List<UserLoginBranchAccessDTO> branchAccessDTOList;
 
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
//	public List<UserLoginClientAccessDTO> getClientAccessDTOList() {
//		return clientAccessDTOList;
//	}
//	public void setClientAccessDTOList(List<UserLoginClientAccessDTO> clientAccessDTOList) {
//		this.clientAccessDTOList = clientAccessDTOList;
//	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
//	public List<UserLoginBranchAccessDTO> getBranchAccessDTOList() {
//		return branchAccessDTOList;
//	}
//	public void setBranchAccessDTOList(List<UserLoginBranchAccessDTO> branchAccessDTOList) {
//		this.branchAccessDTOList = branchAccessDTOList;
//	}
//	public List<UserLoginRoleAccessDTO> getRoleAccessDTO() {
//		return roleAccessDTO;
//	}
//	public void setRoleAccessDTO(List<UserLoginRoleAccessDTO> roleAccessDTO) {
//		this.roleAccessDTO = roleAccessDTO;
//	}
    
    
    

	
}
