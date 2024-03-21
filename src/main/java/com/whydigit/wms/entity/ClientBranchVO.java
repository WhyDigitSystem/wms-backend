package com.whydigit.wms.entity;

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
@Table(name = "clientbranch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientBranchVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientbranchgen")
	@SequenceGenerator(name = "clientbranchgen", sequenceName = "clientbranchVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clientbranchid")
	private Long id;

	@Column(name = "branchcode")
	private String branchcode;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "customerid")
	private CustomerVO customerVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}