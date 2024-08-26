package com.whydigit.wms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "customerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerid")
	private Long id;

	@Column(name = "customer")
	private String customerName;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customershortname")
	private String customerShortName;
	@Column(name = "panno")
	private String panNo;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "mobilenumber")
	private String mobileNumber;
	@Column(name = "gstregistration")
	private String gstRegistration;
	@Column(name = "emailid")
	private String emailId;
	@Column(name = "groupof")
	private String groupOf;
	@Column(name = "tanno")
	private String tanNo;
	@Column(name = "address1")
	private String address1;
	@Column(name = "address2")
	private String address2;
	@Column(name = "gstno")
	private String gstNo;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	

	@JsonManagedReference
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	private List<ClientVO> clientVO;

	@JsonManagedReference
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	private List<ClientBranchVO> clientBranchVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
