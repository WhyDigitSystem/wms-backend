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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dekitting")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeKittingVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dekittinggen")
	@SequenceGenerator(name = "dekittinggen", sequenceName = "dekittingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dekittingid")
	private Long id;
	@Column(name = "transactiontype")
	private String transactionType;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer")
	private String customer;
	@Column(name = "client")
	private String client;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "freeze")
	private String freeze ;
	@Column(name = "grndate")
	private LocalDate grnDate = LocalDate.now();
	
	@OneToMany(mappedBy = "deKittingVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeKittingParentVO> deKittingParentVO;
	
	@OneToMany(mappedBy = "deKittingVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DeKittingChildVO> deKittingChildVO;
}
