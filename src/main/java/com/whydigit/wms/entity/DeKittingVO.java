package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dekitting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeKittingVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dekittinggen")
	@SequenceGenerator(name = "dekittinggen", sequenceName = "dekittingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dekittingid")
	private Long id;
	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "freeze")
	private boolean freeze = true;
	
	@Column(name = "screenname",length =25)
	private String screenName="DE KITTING";
	@Column(name = "screencode",length =10)
	private String screenCode="DK";
	
	
	@OneToMany(mappedBy = "deKittingVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeKittingParentVO> deKittingParentVO;
	
	@OneToMany(mappedBy = "deKittingVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeKittingChildVO> deKittingChildVO;
}
