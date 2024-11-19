package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companygen")
	@SequenceGenerator(name = "companygen", sequenceName = "companyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companyid")
	private Long id;

    @Column(name = "companycode",length =25)
	private String companyCode;
    @Column(name = "companyname",length =150)
	private String companyName;
	@Column(name = "country",length =25)
	private String country;
	@Column(name = "currency",length =25)
	private String currency;
	@Column(name = "maincurrency",length =25)
	private String mainCurrency;
	@Column(name = "address",length =150)
	private String address;
	@Column(name = "zipcode",length =25)
	private String zip;
	@Column(name = "city",length =25)
	private String city;
	@Column(name = "state",length =25)
	private String state;
	@Column(name = "phone",length =25)
	private String phone;
	@Column(name = "email",length =25)
	private String email;
	@Column(name = "website",length =25)
	private String webSite;
	@Column(name = "notes",length =25)
	private String note;
//	@Column(name = "userd")
//	private String userId;
	@Column(name = "active")
	private boolean active;
//	@Column(unique = true)
//	private String dupchk;
	@Column(name = "employeename",length =150)
	private String employeeName;
	@Column(name = "employeecode",length =25)
	private String employeeCode;
	@Column(name = "password")
	private String password;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	private int role;
	@Column(name = "ceo",length =25)
	private String ceo;
	@Column(name = "gst",length =25)
	private String gst;
	
	
	 @JsonGetter("active")
		public String getActive() {
			return active ? "Active" : "In-Active";
		}

		// Optionally, if you want to control serialization for 'cancel' field similarly
		@JsonGetter("cancel")
		public String getCancel() {
			return cancel ? "T" : "F";
		}
	    
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}
