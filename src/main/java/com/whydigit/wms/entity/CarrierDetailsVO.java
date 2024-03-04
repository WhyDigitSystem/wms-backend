package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String addresstype;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private String contact;
	private String phone;
	private String emailid;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "carrierid")
	private CarrierVO carrierVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
