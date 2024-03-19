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
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "regiongen")
	@SequenceGenerator(name = "regiongen",sequenceName = "regionVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="regionid")
	private Long id;
	private String userid;
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String docid;
	private String regioncode;
	private String region;
	private boolean active;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private Long orgid;
	private boolean cancel;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}