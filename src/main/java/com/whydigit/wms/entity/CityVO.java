package com.whydigit.wms.entity;

import javax.persistence.Column;
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
@Table(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "citygen")
	@SequenceGenerator(name = "citygen",sequenceName = "cityVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="cityid")
	private Long id;
	
	@Column(name="citycode", length = 30)
	private String citycode;
	
	@Column(name="country", length = 30)
	private String country;
	
	@Column(name="city", length = 30)
    private String city;
	
	@Column(name="state", length = 30)
    private String state;
	
	@Column(name="active")
    private boolean active;
	
	@Column(name="userif", length = 30)
    private String userid;
	
	@Column(unique = true)
	private String dupchk;
	
	@Column(name="createdby", length = 30)
	private String createdby;
	
	@Column(name="modifiedby", length = 30)
	private String updatedby;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="cancel")
	private boolean cancel;

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
