package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryVO {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long countryid;
	
	@Column(unique = true)
	private String countryname;
	@Column(unique = true)
	private String countrycode;
	private boolean active;
	private String userid;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

}
