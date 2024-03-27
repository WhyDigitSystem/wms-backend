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
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employeegen")
	@SequenceGenerator(name = "employeegen",sequenceName = "employeeVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="employeeid")
	private Long id;
	
	@Column(name="employeecode", length = 30)
	private String employeecode;
	
	@Column(name="employee", length = 30)
	private String employeeName;
	
	@Column(name="gender", length = 30)
	private String gender;
	
	@Column(name="branch", length = 30)
	private String branch;
	
	@Column(name="branchcode", length = 30)
	private String branchcode;
	
	@Column(name="department", length = 30)
	private String department;
	
	@Column(name="designation", length = 30)
	private String designation;
	
	@Column(name="dateofbirth", length = 30)
	private String dateofbirth;
	
	@Column(name="joiningdate", length = 30)
	private String joiningdate;
	
	@Column(unique = true)
	private String dupchk;
	
	@Column(name="createdby", length = 30)
	private String createdby;
	
	@Column(name="modifiedby", length = 30)
	private String updatedby;
	
	@Column(name="orgid", length = 30)
	private Long orgId;
	
	@Column(name="cancel")
	private boolean cancel;

	@Column(name="userid", length = 30)
	private String userid;
	
	@Column(name="cancelremarks", length = 30)
	private String cancelremark;
	
	@Column(name="active")
	private boolean active;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
