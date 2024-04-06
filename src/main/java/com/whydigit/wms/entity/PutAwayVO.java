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
	@SequenceGenerator(name = "putawaygen", sequenceName = "putawayVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawayid")
	private Long id;

	@Column(name = "docdate", length = 30)
	private LocalDate docdate;

	@Column(name = "grnno", length = 30)
	private String grnno;

	@Column(name = "docid", length = 30)
	private String docid;

	@Column(name = "grndate", length = 30)
	private LocalDate grndate;

	@Column(name = "entryno", length = 30)
	private String entryno;

	@Column(name = "core", length = 30)
	private String core;

	@Column(name = "suppliershortname", length = 30)
	private String suppliershortname;

	@Column(name = "supplier", length = 30)
	private String supplier;

	@Column(name = "modeofshipment", length = 30)
	private String modeodshipment;

	@Column(name = "carrier", length = 30)
	private String carrier;

	@Column(name = "locationtype", length = 30)
	private String locationtype;

	@Column(name = "status", length = 30)
	private String status;

	@Column(name = "lotno", length = 30)
	private String lotno;

	@Column(name = "enteredperson", length = 30)
	private String enteredperson;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "company", length = 30)
	private String company;

	@Column(name = "cancel", length = 30)
	private boolean cancel;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;

	@Column(name = "active", length = 30)
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

	@Column(name = "orgid", length = 30)
	private String orgId;

	@Column(name = "warehouse", length = 30)
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
        String fyFull = (currentMonthDay.compareTo("0331") > 0) ?
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")) :
                            LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));
        return fyFull;

    }
}
