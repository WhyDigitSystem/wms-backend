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
@Table(name = "grn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grngen")
	@SequenceGenerator(name = "grngen", sequenceName = "grnVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grnid")
	private Long id;

	@Column(name = "direct", length = 30)
	private String direct;

	@Column(name = "docid", length = 30)
	private String docid;

	@Builder.Default
	private LocalDate docdate = LocalDate.now();

	@Column(name = "entryno", length = 30)
	private String entryno;

	@Column(name = "entrydate", length = 30)
	private LocalDate entrydate;

	@Column(name = "grndate", length = 30)
	private LocalDate grndate;

	@Column(name = "tax", length = 30)
	private String tax;

	@Column(name = "gatepassid", length = 30)
	private String gatepassid;

	@Column(name = "gatepassdate", length = 30)
	private LocalDate gatepassdate;

	@Column(name = "customerpo", length = 30)
	private String customerpo;

	@Column(name = "suppliershortname", length = 30)
	private String suppliershortname;

	@Column(name = "supplier", length = 30)
	private String supplier;

	@Column(name = "carrier", length = 30)
	private String carrier;

	@Column(name = "lotno", length = 30)
	private String lotno;

	@Column(name = "modeofshipment", length = 30)
	private String modeofshipment;

	@Column(name = "noofpackage", length = 30)
	private String noofpackage;

	@Column(name = "screencode", length = 30)
	private String screencode;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "orgid", length = 30)
	private Long orgId;

	@Column(name = "cancel", length = 30)
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

	@Column(name = "client", length = 30)
	private String client;

	@Column(name = "customer", length = 30)
	private String customer;

	@Column(name = "address", length = 30)
	private String address;

	@Column(name = "billofentryno", length = 30)
	private String billofenrtyno;

	@Column(name = "containerno", length = 30)
	private String containerno;

	@Column(name = "fifoflag", length = 30)
	private String fifoflag;

	@Column(name = "warehouse", length = 30)
	private String warehouse;

	@Column(name = "flag", length = 30)
	private String flag;

	@Column(name = "stockdate", length = 30)
	private LocalDate stockdate;

	@Column(name = "vas")
	private boolean vas;

	@Column(name = "vehicleno", length = 30)
	private String vehicleno;

	@Column(name = "vehicledetails", length = 30)
	private String vehicledetails;

	@Column(name = "finyr", length = 30)
	private String finyr;

	@Column(name = "noofpackages", length = 30)
	private String noofpackages;

	@Column(name = "totalamount", length = 30)
	private String totalamount;

	@Column(name = "totalgrnqty")
	private int totalgrnqty;//

	@Column(name = "billofentry", length = 30)
	private String billofentry;

	@Column(name = "capacity", length = 30)
	private String capacity;

	@Column(name = "sealno", length = 30)
	private String sealno;

	@Column(name = "vesselno", length = 30)
	private String vesselno;

	@Column(name = "hsnno", length = 30)
	private String hsnno;

	@Column(name = "securityname", length = 30)
	private String securityname;

	@Column(name = "vehicletype", length = 30)
	private String vehicletype;

	@Column(name = "vessedetails", length = 30)
	private String vesseldetails;

	@Column(name = "lrno", length = 30)
	private String lrno;

	@Column(name = "drivername", length = 30)
	private String drivername;

	@Column(name = "contact", length = 30)
	private String contact;

	@Column(name = "lrdate", length = 30)
	private LocalDate lrdate;

	@Column(name = "goodsdescripition", length = 30)
	private String goodsdescripition;

	@Column(name = "destinatiomfrom", length = 30)
	private String destinationfrom;

	@Column(name = "destinationto", length = 30)
	private String destinationto;

	@Column(name = "noofpallets", length = 30)
	private String noofpallets;

	@Column(name = "invoiceno", length = 30)
	private String invoiceno;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	private List<GrnDetailsVO> grnDetailsVO;

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
