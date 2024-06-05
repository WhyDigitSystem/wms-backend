package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalparamgen")
	@SequenceGenerator(name = "globalparamgen", sequenceName = "globalparamVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "globalparamid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "client", length = 30)
	private String client;
}
