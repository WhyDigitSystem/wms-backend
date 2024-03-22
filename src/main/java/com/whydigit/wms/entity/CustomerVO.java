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
	@SequenceGenerator(name = "customergen", sequenceName = "customerVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerid")
	private Long id;

	@Column(name = "customer")
	private String customer;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "customershortname")
	private String customershortname;

	@Column(name = "panno")
	private String panno;

	@Column(name = "contactperson")
	private String contactperson;

	@Column(name = "mobilenumber")
	private String mobilenumber;

	@Column(name = "gstregistration")
	private String gstregistration;

	@Column(name = "emailid")
	private String emailid;

	@Column(name = "groupof")
	private String groupof;

	@Column(name = "tanno")
	private String tanno;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "gstno")
	private String gstno;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "cancelremarks")
	private String cancelremarks;

	@Column(name = "createdby")
	private String createdby;

	@Column(name = "modifiedby")
	private String updatedby;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "active")
	private boolean active;

	@Column(name = "userid")
	private String userid;

	@Column(name = "cancel")
	private boolean cancel;

	@JsonManagedReference
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	private List<ClientVO> clientVO;

	@JsonManagedReference
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	private List<ClientBranchVO> clientBranchVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
