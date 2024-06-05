package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countrygen")
	@SequenceGenerator(name = "countrygen", sequenceName = "countryVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "countryid")
	private Long id;

	@Column(name = "country", length = 30)
	private String countryname;
	@Column(name = "countrycode", length = 30)
	private String countrycode;
	@Column(name = "active")
	private boolean active;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "cancel")
	private boolean cancel;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
