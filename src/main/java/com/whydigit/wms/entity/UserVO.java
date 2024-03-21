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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "usersgen")
	@SequenceGenerator(name = "usersgen",sequenceName = "usersVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="usersid")
	private Long usersid;
	@Column(unique = true)
	private String userid;
	private String username;
	private String password;
	private String employeename;
	private String nickname;
	private String email;
	private Long orgid;
	private String mobileno;
	private String usertype;
	private String customer;
	private String warehouse;
	private String branch;
	private String branchcode;
	private String client;
	private boolean loginstatus;
	private String active;
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
