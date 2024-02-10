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
@Table(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityid;
	
	@Column(unique = true)
	private String citycode;
	private String country;
	@Column(unique = true)
    private String cityname;
    private String state;
    private boolean active;
    private String userid;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
