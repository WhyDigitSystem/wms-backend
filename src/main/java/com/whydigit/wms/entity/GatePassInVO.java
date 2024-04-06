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
	@SequenceGenerator(name = "gatepassingen", sequenceName = "gatepassinVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatepassinid")
	private Long id;

	@Column(name = "transactiontype", length = 30)
	private String transactiontype;

	@Column(name = "entryno", length = 30)
	private String entryno;//

	@Column(name = "orgid", length = 30)
	private Long orgId;

	@Column(name = "docid")
	private Long docid;

	@Column(name = "docdate", length = 30)
	private LocalDate docdate = LocalDate.now();

	@Column(name = "date", length = 30)
	private LocalDate date;

	@Column(name = "supplier", length = 30)
	private String supplier;

	@Column(name = "suppliershortname", length = 30)
	private String suppliershortname;

	@Column(name = "modeofshipment", length = 30)
	private String modeofshipment;

	@Column(name = "carrier", length = 30)
	private String carrier;

	@Column(name = "carriertransport", length = 30)
	private String carriertransport;

	@Column(name = "vehicletype", length = 30)
	private String vehicletype;

	@Column(name = "vehicleno", length = 30)
	private String vehicleno;

	@Column(name = "drivername", length = 30)
	private String drivername;

	@Column(name = "contact", length = 30)
	private String contact;

	@Column(name = "goodsdescription", length = 30)
	private String goodsdescription;

	@Column(name = "securityname", length = 30)
	private String securityname;

	@Column(name = "lotno", length = 30)
	private String lotno;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "company", length = 30)
	private String company;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;

	@Column(name = "active")
	private boolean active;

	@Column(name = "branchcode", length = 30)
	private String branchcode;

	@Column(name = "branch", length = 30)
	private String branch;

	@Column(name = "screencode", length = 30)
	private String screencode;

	@Column(name = "client", length = 30)
	private String client;

	@Column(name = "customer", length = 30)
	private String customer;

	@Column(name = "finyr", length = 30)
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
