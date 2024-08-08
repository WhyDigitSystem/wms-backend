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
@Table(name = "locationtype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationtypegen")
	@SequenceGenerator(name = "locationtypegen", sequenceName = "locationtypeseq", initialValue = 1000000001, allocationSize = 1)
	private Long id;
	@Column(name="locationtype")
	private String binType;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="updatedby")
	private String updatedBy;
	@Column(name="orgid")
	private Long orgId;
	private boolean cancel;
	private boolean active;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
