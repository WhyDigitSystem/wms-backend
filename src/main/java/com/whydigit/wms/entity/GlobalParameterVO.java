package com.whydigit.wms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "globalparam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParameterVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long globalid;
	private String company;
	private String userid;
	private String warehouse;
	private String branchname;
	private String branchcode;
	private String customer;
	private String client;
	
}
