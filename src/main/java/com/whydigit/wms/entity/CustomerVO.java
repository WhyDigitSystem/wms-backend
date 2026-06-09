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

	@Column(name = "customer",length =150)
	private String customerName;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customershortname",length =150)
	private String customerShortName;
	@Column(name = "panno",length =25)
	private String panNo;
	@Column(name = "contactperson",length =150)
	private String contactPerson;
	@Column(name = "mobilenumber",length =25)
	private String mobileNumber;
	@Column(name = "gstregistration",length =25)
	private String gstRegistration;
	@Column(name = "emailid",length =25)
	private String emailId;
	@Column(name = "groupof",length =25)
	private String groupOf;
	@Column(name = "tanno",length =25)
	private String tanNo;
	@Column(name = "address1",length =150)
	private String address1;
	@Column(name = "address2",length =150)
	private String address2;
	@Column(name = "gstno",length =25)
	private String gstNo;
	@Column(name = "city",length =25)
	private String city;
	@Column(name = "state",length =25)
	private String state;
	@Column(name = "country",length =25)
	private String country;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
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
