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
	private String employeecode;
	private String employee;
	private String gender;
	private String branch;
	private String branchcode;
	private String department;
	private String designation;
	private String dateofbirth;
	private String joiningdate;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private Long orgid;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
