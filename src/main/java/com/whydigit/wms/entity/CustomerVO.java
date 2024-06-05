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

	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customershortname", length = 30)
	private String customershortname;
	@Column(name = "panno", length = 30)
	private String panno;
	@Column(name = "contactperson", length = 30)
	private String contactperson;
	@Column(name = "mobilenumber", length = 30)
	private String mobilenumber;
	@Column(name = "gstregistration", length = 30)
	private String gstregistration;
	@Column(name = "emailid", length = 30)
	private String emailid;
	@Column(name = "groupof", length = 30)
	private String groupof;
	@Column(name = "tanno", length = 30)
	private String tanno;
	@Column(name = "address1", length = 255)
	private String address1;
	@Column(name = "address2", length = 255)
	private String address2;
	@Column(name = "gstno", length = 30)
	private String gstno;
	@Column(name = "city", length = 30)
	private String city;
	@Column(name = "state", length = 30)
	private String state;
	@Column(name = "country", length = 30)
	private String country;
	@Column(name = "cancelremarks", length = 30)
	private String cancelremarks;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
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
