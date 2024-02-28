package com.whydigit.wms.entity;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userlogin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userloginid;
	private String userid;
	private String name;
	private String password;
	private String usertype;
	private String email;
	private String mobile;
	private String globaldisplaytext;
	private String homepage;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String cancelremark;
	private boolean active;

	@JsonManagedReference
	@OneToMany(mappedBy = "userLoginVO", cascade = CascadeType.ALL)
	private List<UserLoginRolesVO> rolesVO;

	@OneToMany(mappedBy = "userLoginVO", cascade = CascadeType.ALL)
	private List<UserLoginClientAccessVO> clientAccessVO;

	@OneToMany(mappedBy = "userLoginVO", cascade = CascadeType.ALL)
	private List<UserLoginBranchAccessibleVO> branchAccessibleVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
