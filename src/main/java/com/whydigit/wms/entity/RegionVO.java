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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regiongen")
	@SequenceGenerator(name = "regiongen", sequenceName = "regionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "regionid")
	private Long id;

	@Column(name = "userid")
	private String userid;
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String docid;
	@Column(name = "regioncode")
	private String regioncode;
	@Column(name = "region")
	private String regionname;
	@Column(name = "active")
	private boolean active;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}