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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vaspick")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VasPickVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaspickgen")
	@SequenceGenerator(name = "vaspickgen", sequenceName = "vaspickseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vaspickid")
	private Long id;
	@Column(name = "picbin",length =25)
	private String picBin;
	@Column(name = "screenname",length =25)
	private String screenName="VAS PICK";
	@Column(name = "screencode",length =10)
	private String screenCode="VPR";
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docid",unique =true,length =25)
	private String docId;
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
	private boolean freeze;
	@Column(name = "totalorderqty")
	private int totalOrderQty;
	@Column(name = "pickedqty")
	private int pickedQty;
	@Column(name = "statestatus",length =10)
    private String stateStatus;
	@Column(name = "stockstate",length =25)
	private String stockState;
	@Column(name = "status",length =10)
	private String status;
	
	
	@OneToMany(mappedBy ="vasPickVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<VasPickDetailsVO> vasPickDetailsVO;
	

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
