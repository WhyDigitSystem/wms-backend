package com.whydigit.wms.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;

import com.whydigit.wms.entity.ResponsibilityVO;
import com.whydigit.wms.entity.UserLoginRolesVO;

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
	private List<UserLoginRolesVO> roleVO;
	private List<Map<String, Object>> responsibilityVO;
	private List<Map<String, Object>> screensVO;// Changed type to List<Map<String, Object>>
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    private Date accountRemovedDate;
    private String token;
    private String tokenId;

    // Setter method to accept List<Map<String, Object>>
    public void setResponsibilityVO(List<Map<String, Object>> responsibilityVOList) {
        this.responsibilityVO = responsibilityVOList;
    }
    
    // Getter methods (if necessary)
    public List<Map<String, Object>> getResponsibilityVO() {
        return responsibilityVO;
    }
    
    public void setScreensVO(List<Map<String, Object>> screensVOList) {
        this.screensVO = screensVOList;
    }

    public List<Map<String, Object>> getScreensVO() {
        return screensVO;
    }
   
}
