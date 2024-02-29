package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_branchaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginBranchAccessibleVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long branchaccessibleid;
	private String branch;
	private String branchcode;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "usersId")
    private UserVO userVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}