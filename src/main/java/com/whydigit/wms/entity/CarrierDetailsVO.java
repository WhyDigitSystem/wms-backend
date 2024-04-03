package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrierdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carrierdetailsgen")
	@SequenceGenerator(name = "carrierdetailsgen", sequenceName = "carrierdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "carrierdetailsid")
	private Long id;

	@Column(name = "addresstype", length = 30)
	private String addresstype;

	@Column(name = "address", length = 30)
	private String address;

	@Column(name = "state", length = 30)
	private String state;

	@Column(name = "zipcode", length = 30)
	private String zipcode;

	@Column(name = "country", length = 30)
	private String country;

	@Column(name = "contact", length = 30)
	private String contact;

	@Column(name = "phone", length = 30)
	private String phone;

	@Column(name = "emailid", length = 30)
	private String emailid;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "carrierid")
	private CarrierVO carrierVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
