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
	private Long id;
		private String itemtype;
		private String partno;
		private String partdesc;
		private String custpartno;
		private String groupname;
		private String barcode;
		private String stylecode;
		private String basesku;
		private String purchaseunit;
		private String storageunit;
		private String fsn;
		private String saleunit;
		private String type;
		private String sku;
		private String ssku;
		private String weightofskuanduom;
		private String hsncode;
		private String parentchildkey;
		private String cbranch;
		private String criticalstocklevel;
	private String bchk;
		private String status;
	private Long orgId;
	private String customer;
	private String client;
	private String warehouse;
	private String branch;
	private String branchcode;
	private String userid;
		private String palletqty;
	private boolean active;
	private boolean cancel;
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
