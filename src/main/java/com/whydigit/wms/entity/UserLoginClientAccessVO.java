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
@Table(name = "ul_clientaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginClientAccessVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long clientaccessid;
	private String client;
	private String customer;
	private boolean active;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "userloginid")
    private UserLoginVO userLoginVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
