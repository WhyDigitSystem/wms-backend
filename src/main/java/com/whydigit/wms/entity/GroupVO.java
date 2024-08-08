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
@Table(name = "itemgroup")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "itemgroupgen")
	@SequenceGenerator(name = "itemgroupgen",sequenceName = "itemgroupseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="itemgroupid")
	private Long id;
	@Column(name = "groupname")
	private String groupName;
	private boolean active;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	private String company;
	private boolean cancel;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}