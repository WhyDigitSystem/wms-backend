package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manualpick")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualPickVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manualpickgen")
	@SequenceGenerator(name = "manualpickgen", sequenceName = "manualpickgen", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "manualpickid")
	private Long id;

	@Column(name = "transactiontype")
	private String transactiontype;
	@Column(name = "docid",unique = true)
	private String docid;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "buyerrefno")
	private String buyerrefno;
	@Column(name = "buyerorderno")
	private String buyerorderno;
	@Column(name = "buyerrefdate")
	private LocalDate buyerrefdate;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "clientname")
	private String clientname;
	@Column(name = "shortname")
	private String shortname;
	@Column(name = "clientaddress")
	private String clientaddress;
	@Column(name = "customername")
	private String customername;
	@Column(name = "customeraddress")
	private String customeraddress;
	@Column(name = "noofboxes")
	private String noofboxes;
	@Column(name = "duedays")
	private String duedays;
	@Column(name = "outtime")
	private String outtime;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "cretaedby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "company")
	private String company;
	@Column(name = "camcel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremarks")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "screencode")
	private String screencode;

	@JsonManagedReference
	@OneToMany(mappedBy = "manualPickVO")
	private List<ManualPickDetailsVO> manualPickDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
