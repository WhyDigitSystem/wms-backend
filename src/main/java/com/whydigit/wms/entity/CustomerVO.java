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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerid;
	private String customer;
	private String company;
	private String shortname;
	private String panno;
	private String contactperson;
	private String landlinenumber;
	private String mobilenumber;
	private String gstregistration;
	private String emailid;
	private String groupof;
	private String tanno;
	private String address1;
	private String address2;
	private String gstno;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private String fax;
	private String cancel;
	private String cancelremarks;
	private String createdby;
	private String updatedby;
	@Column(unique = true)
	private String dupchk;
	private boolean active;
	private String userid;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
    private List<ClientVO> clientVO;

	@JsonManagedReference
    @OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
    private List<ClientBranchVO> clientBranchVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
