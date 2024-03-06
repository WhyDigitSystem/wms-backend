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
@Table(name = "grn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String direct;
	private String docid;
	private LocalDate docdate = LocalDate.now();
	private String entryno;
	private LocalDate entrydate;
	private String tax;
	private String gatepassid;
	private LocalDate gatepassdate;
	private String customerpo;
	private String suppliershortname;
	private String supplier;
	private String carrier;
	private String lotno;
	private String modeofshipment;
	private String noofpackage;
	private String totalamount;
	private String totalgrnqty;
	private String screencode;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private Long orgId;
	private String cancel;
	private String userid;
	private String cancelremark;
	private String active;
	private String branchcode;
	private String branchname;
	private String client;
	private String customer;
	private String address;
	private String billofenrtyno;
	private String contailnerno;
	private String fifoflag;
	private String warehouse;
	private String flag;
	private String source;
	private String screen;
	private String stockdate;
	private String vas;
	private String vehicleno;
	private String vehicledetails;
	private String vesselno;

	@JsonManagedReference
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	List<GrnDetailsVO> grnDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
