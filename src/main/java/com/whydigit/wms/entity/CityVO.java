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
	private String citycode;
	private String country;
    private String city;
    private String state;
    private boolean active;
    private String userid;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private Long orgid;
	private boolean cancel;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
