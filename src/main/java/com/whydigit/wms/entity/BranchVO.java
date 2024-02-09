package com.whydigit.wms.entity;

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
@Table(name = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchid;
	private String branchname;
	private String branchcode;
    private String branchaddress;
    private String company;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private String email;
    private String branchGST;
    private String locationgtype;
    private boolean active;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
