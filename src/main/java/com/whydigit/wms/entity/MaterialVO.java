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
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialgen")
	@SequenceGenerator(name = "materialgen", sequenceName = "materialVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "materialid")
	private Long id;

	@Column(name = "itemtype", length = 30)
	private String itemtype;
	@Column(name = "partno", length = 30)
	private String partno;
	@Column(name = "partdesc", length = 30)
	private String partdesc;
	@Column(name = "custpartno", length = 30)
	private String custpartno;
	@Column(name = "groupname", length = 30)
	private String groupname;
	@Column(name = "barcode", length = 30)
	private String barcode;
	@Column(name = "stylecode", length = 30)
	private String stylecode;
	@Column(name = "basesku", length = 30)
	private String basesku;
	@Column(name = "purchaseunit", length = 30)
	private String purchaseunit;
	@Column(name = "storageunit", length = 30)
	private String storageunit;
	@Column(name = "fsn", length = 30)
	private String fsn;
	@Column(name = "saleunit", length = 30)
	private String saleunit;
	@Column(name = "type", length = 30)
	private String type;
	@Column(name = "sku", length = 30)
	private String sku;
	@Column(name = "skuqty", length = 30)
	private String skuqty;
	@Column(name = "ssku", length = 30)
	private String ssku;
	@Column(name = "sskuqty", length = 30)
	private String sskuqty;
	@Column(name = "weightofskuanduom", length = 30)
	private String weightofskuanduom;
	@Column(name = "hsncode", length = 30)
	private String hsncode;
	@Column(name = "parentchildkey", length = 30)
	private String parentchildkey;
	@Column(name = "cbranch", length = 30)
	private String cbranch;
	@Column(name = "criticalstocklevel", length = 30)
	private String criticalstocklevel;
	@Column(name = "status", length = 30)
	private String status;
	@Column(name = "orgid", length = 30)
	private Long orgId;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "client", length = 30)
	private String client;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
  @Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "palletqty", length = 30)
	private String palletqty;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "length")
	private Float length;
	@Column(name = "breadth")
	private Float breadth;
	@Column(name = "height")
	private Float height;
	@Column(name = "weight")
	private Float weight;
	@Column(unique = true)
	private String dupchk;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
