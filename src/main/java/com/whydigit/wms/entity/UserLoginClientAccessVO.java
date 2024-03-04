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
@Table(name = "user_clientaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginClientAccessVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String client;
	private String customer;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "usersId")
    private UserVO userVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
