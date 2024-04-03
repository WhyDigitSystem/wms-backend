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
	@SequenceGenerator(name = "manualpickgen", sequenceName = "manualpickVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "manualpickid")
	private Long id;

	@Column(name = "transactiontype", length = 30)
	private String transactiontype;

	@Column(name = "docid", length = 30)
	private String docid;

	@Column(name = "docdate", length = 30)
	private LocalDate docdate;

	@Column(name = "buyerrefno", length = 30)
	private String buyerrefno;

	@Column(name = "buyerorderno", length = 30)
	private String buyerorderno;

	@Column(name = "buyerrefdate", length = 30)
	private LocalDate buyerrefdate;

	@Column(name = "invoiceno", length = 30)
	private String invoiceno;

	@Column(name = "clientname", length = 30)
	private String clientname;

	@Column(name = "shortname", length = 30)
	private String shortname;

	@Column(name = "clientaddress", length = 30)
	private String clientaddress;

	@Column(name = "customername", length = 30)
	private String customername;

	@Column(name = "customeraddress", length = 30)
	private String customeraddress;

	@Column(name = "noofboxes", length = 30)
	private String noofboxes;

	@Column(name = "duedays", length = 30)
	private String duedays;

	@Column(name = "outtime", length = 30)
	private String outtime;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "cretaedby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "company", length = 30)
	private String company;

	@Column(name = "camcel")
	private boolean cancel;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;

	@Column(name = "active")
	private boolean active;

	@Column(name = "screencode", length = 30)
	private String screencode;

	@JsonManagedReference
	@OneToMany(mappedBy = "manualPickVO")
	private List<ManualPickDetailsVO> manualPickDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
