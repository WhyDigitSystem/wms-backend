package com.whydigit.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.time.LocalDate;

import javax.persistence.CascadeType;

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
	private Long grnid;
	private String direct;
	private String docid;
	private LocalDate docdate;
	private String entryno;
	private String date;
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
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private String cancel;
	private String userid;
	private String cancelremark;
	private String active;
	private String branchcode;
	private String branchname;
	private String client;
	private String customer;
	private String batchno;
	private String batchdt;
	private String locationtype;
	private String partcode;
	private String qcflag;
	private String shipmentno;
	private String sqty;
	private String wm_itemid;

	@JsonManagedReference
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	GrnDetailsVO grnDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
