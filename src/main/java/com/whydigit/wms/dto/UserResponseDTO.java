package com.whydigit.wms.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private Long usersId;
	private String userName;
	private String employeeName;
	private String email;
	private String userType;
	private Long orgId;
	private String customer;
	private String warehouse;
	private String branch;
	private String client;
	private boolean loginStatus;
	private boolean active;
	private List<Map<String, Object>> roleVO;
	private List<Map<String, Object>> responsibilityVO;
	private List<Map<String, Object>> screensVO;
	
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    private Date accountRemovedDate;
    private String token;
    private String tokenId;
	

    // Setter method to accept List<Map<String, Object>>
   
   
}
