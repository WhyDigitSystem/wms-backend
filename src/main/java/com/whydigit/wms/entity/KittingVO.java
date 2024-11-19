package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kitting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KittingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kittinggen")
	@SequenceGenerator(name = "kittinggen", sequenceName = "kittingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "kittingid")
	private Long id;

	@Column(name = "docid", unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length=150)
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
	@Column(name = "refno",length =25)
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate = LocalDate.now();

	@Column(name = "screenname",length =25)
	private String screenName = "KITTING";
	@Column(name = "screencode",length =10)
	private String screenCode = "KT";

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@OneToMany(mappedBy = "kittingVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<KittingDetails1VO> kittingDetails1VO;

	@OneToMany(mappedBy = "kittingVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<KittingDetails2VO> kittingDetails2VO;

	@PrePersist
	private void setDefaultFinyr() {
		// Execute the logic to set the default value for finyr
		String fyFull = calculateFinyr();
		this.finYear = fyFull;
	}

	private String calculateFinyr() {
		// Logic to calculate finyr based on the provided SQL query
		String currentMonthDay = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"));
		String fyFull = (currentMonthDay.compareTo("0331") > 0)
				? LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"))
				: LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));
		return fyFull;

	}
}
