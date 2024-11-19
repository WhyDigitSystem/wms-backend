package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "designation")
@Entity
public class DesignationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designationgen")
	@SequenceGenerator(name = "designationgen", sequenceName = "designationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "designationid")
	private Long id;
	@Column(name = "designation",length =25)
	private String designation;
	@Column(name="orgid")
    private Long orgId;
	private boolean active;
	private boolean cancel;
	
	@Column(name="createdby",length =25)
	private String createdBy;
	@Column(name="modifiedby",length =25)
	private String updatedBy;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}