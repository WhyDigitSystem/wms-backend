package com.whydigit.wms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;
import com.whydigit.wms.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long usersId;
	@Column(unique = true)
	private String userName;
	private String password;
	private String employeeName;
	private String nickName;
	private String email;
	private Long orgId;
	private String mobileNo;
	private String userType;
	private String customer;
	private String warehouse;
	private String branch;
	private String branchcode;
	private String client;
	private boolean loginStatus;
	private String isActive;
	private Role role;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@OneToMany(mappedBy = "userVO",cascade = CascadeType.ALL)
	private List<UserLoginRolesVO>roleAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	private List<UserLoginClientAccessVO> clientAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	private List<UserLoginBranchAccessibleVO> branchAccessibleVO;

	private Date accountRemovedDate;
	
}
