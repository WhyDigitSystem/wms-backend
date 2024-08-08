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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putaway")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "putawaygen")
	@SequenceGenerator(name = "putawaygen", sequenceName = "putawayseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawayid")
	private Long id;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "grnno")
	private String grnno;
	@Column(name = "docid")
	private String docid;
	@Column(name = "grndate")
	private LocalDate grndate;
	@Column(name = "entryno")
	private String entryno;
	@Column(name = "core")
	private String core;
	@Column(name = "suppliershortname")
	private String suppliershortname;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "modeofshipment")
	private String modeodshipment;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "locationtype")
	private String locationtype;
	@Column(name = "status")
	private String status;
	@Column(name = "lotno")
	private String lotno;
	@Column(name = "enteredperson")
	private String enteredperson;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremarks")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "screencode")
	private String screencode;
	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;
	@Column(name = "finyr")
	private String finyr;
	@Column(name = "orgid")
	private String orgId;
	@Column(name = "warehouse")
	private String warehouse;

	@JsonManagedReference
	@OneToMany(mappedBy = "putAwayVO", cascade = CascadeType.ALL)
	private List<PutAwayDetailsVO> putAwayDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@PrePersist
	private void setDefaultFinyr() {
		// Execute the logic to set the default value for finyr
		String fyFull = calculateFinyr();
		this.finyr = fyFull;
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
