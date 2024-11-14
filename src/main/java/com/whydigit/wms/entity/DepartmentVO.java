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

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "departmentgen")
	@SequenceGenerator(name = "departmentgen",sequenceName = "departmentseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="deptid")
	private Long id;
	@Column(name = "departmentname",length =25)
	private String departmentName;
	@Column(name = "deptcode",length =25)
	private String code;
	private boolean active;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	private boolean cancel;
	
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

