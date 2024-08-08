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
	@SequenceGenerator(name = "grngen", sequenceName = "grnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grnid")
	private Long id;

	@Column(name = "direct")
	private String direct;
	@Column(name = "docid")
	private String docId;
	@Builder.Default
	private LocalDate docdate = LocalDate.now();
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "grndate")
	private LocalDate grndDate;
	@Column(name = "tax")
	private String tax;
	@Column(name = "gatepassid")
	private String gatePassId;
	@Column(name = "gatepassdate")
	private LocalDate gatePassDate;
	@Column(name = "customerpo")
	private String customerPo;
	@Column(name = "suppliershortname")
	private String supplierShortName;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	@Column(name = "noofpackage")
	private String noOfPackage;
	
	@Builder.Default
	@Column(name = "screencode")
	private String screenCode="GRN";
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "orgid")
	private Long orgId;
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
	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;
	@Column(name = "address")
	private String address;
	@Column(name = "billofentryno")
	private String billofenrtyno;
	@Column(name = "containerno")
	private String containerno;
	@Column(name = "fifoflag")
	private String fifoflag;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "flag")
	private String flag;
	@Column(name = "stockdate")
	private LocalDate stockdate;
	@Column(name = "vas")
	private boolean vas;
	@Column(name = "vehicleno")
	private String vehicleno;
	@Column(name = "vehicledetails")
	private String vehicledetails;
	@Column(name = "finyr")
	private String finyr;
	@Column(name = "noofpackages")
	private String noofpackages;
	@Column(name = "totalamount")
	private String totalamount;
	@Column(name = "totalgrnqty")
	private int totalgrnqty;//
	@Column(name = "billofentry")
	private String billofentry;
	@Column(name = "capacity")
	private String capacity;
	@Column(name = "sealno")
	private String sealno;
	@Column(name = "vesselno")
	private String vesselno;
	@Column(name = "hsnno")
	private String hsnno;
	@Column(name = "securityname")
	private String securityname;
	@Column(name = "vehicletype")
	private String vehicletype;
	@Column(name = "vessedetails")
	private String vesseldetails;
	@Column(name = "lrno")
	private String lrno;
	@Column(name = "drivername")
	private String drivername;
	@Column(name = "contact")
	private String contact;
	@Column(name = "lrdate")
	private LocalDate lrdate;
	@Column(name = "goodsdescripition")
	private String goodsdescripition;
	@Column(name = "destinatiomfrom")
	private String destinationfrom;
	@Column(name = "destinationto")
	private String destinationto;
	@Column(name = "noofpallets")
	private String noofpallets;
	@Column(name = "invoiceno")
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
