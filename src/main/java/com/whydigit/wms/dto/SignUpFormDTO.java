package com.whydigit.wms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {

	private String userName;
    private String password;
    private String employeeName;
    private String nickName; 
    private String email;
    private Long orgId;
    private String mobileNo;
    private String userType;
    private String isActive;
    private List<UserLoginRoleAccessDTO>roleAccessDTO;
    private List<UserLoginClientAccessDTO> clientAccessDTOList;
    private List<UserLoginBranchAccessDTO> branchAccessDTOList;
 
	
	
}
