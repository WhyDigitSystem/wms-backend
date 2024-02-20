package com.whydigit.wms.entity;

import javax.persistence.Column;
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
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long materialid;
	private String partno;
	private String partdesc;
	private String sku;
	private String ssku;
	private String company;
	private String customer;
	private String client;
	private String warehouse;
	private String branchname;
	private String branchcode;
	private String userid;
	private String palletqty;
	private String cbranch;
	private boolean active;
	private boolean cancel;
	private String parentchild;
	private String createdby;
	private String updatedby;
	private Float length;
	private Float breadth;
	private Float height;
	private Float weight;
	@Column(unique = true)
	private String dupchk;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
		
	
}
