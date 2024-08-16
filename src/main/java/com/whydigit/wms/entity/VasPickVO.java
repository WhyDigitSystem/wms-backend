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
	@Column(name = "picbin")
	private String picBin;
	@Column(name = "screenname")
	private String screenName="VASPICK";
	@Column(name = "screencode")
	private String screenCode="VP";
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docid",unique =true)
	private String docId;
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
	private boolean freeze;
	@Column(name = "totalorderqty")
	private int totalOrderQty;
	@Column(name = "pickedqty")
	private int pickedQty;
	@Column(name = "statestatus")
    private char stateStatus;
	@Column(name = "stockState")
	private String stockState;
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
