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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeegen")
	@SequenceGenerator(name = "employeegen", sequenceName = "employeeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeeid")
	private Long id;

	@Column(name = "employeecode")
	private String employeecode;
	@Column(name = "employee")
	private String employeename;
	@Column(name = "gender")
	private String gender;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "department")
	private String department;
	@Column(name = "designation")
	private String designation;
	@Column(name = "dateofbirth")
	private String dateofbirth;
	@Column(name = "joiningdate")
	private String joiningdate;
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
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremarks")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
