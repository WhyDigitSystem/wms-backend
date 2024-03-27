package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_rolesaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRolesVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userloginrolesgen")
	@SequenceGenerator(name = "userloginroles",sequenceName = "userloginrolesVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="userloginrolesid")
	private long id;
	
	@Column(name="role")
	private String role;
	
	@Column(name="startdate")
	private LocalDate startdate;
	
	@Column(name="enddate")
	private LocalDate enddate;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "usersId")
    private UserVO userVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
