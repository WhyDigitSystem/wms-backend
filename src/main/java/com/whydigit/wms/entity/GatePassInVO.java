package com.whydigit.wms.entity;

import java.time.LocalDate;
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
@Table(name = "gatepassin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePassInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String transactiontype;
	private String entryno;
	private String orgId;
	private LocalDate docdate = LocalDate.now();
	private LocalDate date;
	private String supplier;
	private String modeofshipment;
	private String carriertransport;
	private String vehicletype;
	private String vehicleno;
	private String drivername;
	private String contact;
	private String goodsdescription;
	private String securityname;
	private String lotno;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;                                                                                           
	private String branchcode;
	private String branch;
	private String screencode;
	private String client;
	private String customer;

	@JsonManagedReference
	@OneToMany(mappedBy = "gatePassInVO", cascade = CascadeType.ALL)
	private List<GatePassLrNoDetailsVO> GatePassDetailsList;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
