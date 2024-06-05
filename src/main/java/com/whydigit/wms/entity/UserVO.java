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
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersgen")
	@SequenceGenerator(name = "usersgen", sequenceName = "usersVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "userid")
	private Long id;

	@Column(name = "username", length = 30)
	private String userName;
	@Column(name = "password", length = 255)
	private String password;
	@Column(name = "employeename", length = 30)
	private String employeeName;
	@Column(name = "nickname", length = 30)
	private String nickName;
	@Column(name = "email", length = 255)
	private String email;
	@Column(name = "orgid", length = 30)
	private Long orgId;
	@Column(name = "mobileno", length = 30)
	private String mobileNo;
	@Column(name = "usertype", length = 30)
	private String userType;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "client", length = 30)
	private String client;
	@Column(name = "loginstatus")
	private boolean loginStatus;
	@Column(name = "isActive", length = 30)
	private String isActive;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "role")
	private Role role;
	private Role usersId;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	private List<UserLoginRolesVO> roleAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	private List<UserLoginClientAccessVO> clientAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	private List<UserLoginBranchAccessibleVO> branchAccessibleVO;

	private Date accountRemovedDate;

}
