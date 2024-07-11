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
@Table(name = "gatepassin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePassInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gatepassingen")
	@SequenceGenerator(name = "gatepassingen", sequenceName = "gatepassinseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatepassinid")
	private Long id;

	@Column(name = "transactiontype")
	private String transactiontype;
	@Column(name = "entryno")
	private String entryno;//
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docid")
	private Long docid;
	@Column(name = "docdate")
	private LocalDate docdate = LocalDate.now();
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "suppliershortname")
	private String suppliershortname;
	@Column(name = "modeofshipment")
	private String modeofshipment;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "carriertransport")
	private String carriertransport;
	@Column(name = "vehicletype")
	private String vehicletype;
	@Column(name = "vehicleno")
	private String vehicleno;
	@Column(name = "drivername")
	private String drivername;
	@Column(name = "contact")
	private String contact;
	@Column(name = "goodsdescription")
	private String goodsdescription;
	@Column(name = "securityname")
	private String securityname;
	@Column(name = "lotno")
	private String lotno;
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

	@JsonManagedReference
	@OneToMany(mappedBy = "gatePassInVO", cascade = CascadeType.ALL)
	private List<GatePassInDetailsVO> gatePassDetailsList;

	@Embedded
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
