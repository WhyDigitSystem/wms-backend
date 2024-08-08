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
	@SequenceGenerator(name = "materialgen", sequenceName = "materialseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "materialid")
	private Long id;

	@Column(name = "itemtype")
	private String itemType;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "custpartno")
	private String custPartno;
	@Column(name = "groupname")
	private String groupName;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "stylecode")
	private String styleCode;
	@Column(name = "basesku")
	private String baseSku;
	@Column(name = "purchaseunit")
	private String purchaseUnit;
	@Column(name = "storageunit")
	private String storageUnit;
	@Column(name = "fsn")
	private String fsn;
	@Column(name = "salenit")
	private String saleUnit;
	@Column(name = "type")
	private String type;
	@Column(name = "sku")
	private String sku;
	@Column(name = "skuty")
	private String skuQty;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "sskuqty")
	private String sskuQty;
	@Column(name = "weightofskuanduom")
	private String weightofSkuAndUom;
	@Column(name = "hsncode")
	private String hsnCode;
	@Column(name = "parentchildkey")
	private String parentChildKey;
	@Column(name = "cbranch")
	private String cbranch;
	@Column(name = "criticalstocklevel")
	private String criticalStockLevel;
	@Column(name = "status")
	private String status;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer")
	private String customer;
	@Column(name = "client")
	private String client;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "palletqty")
	private String palletQty;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "length")
	private Float length;
	@Column(name = "breadth")
	private Float breadth;
	@Column(name = "height")
	private Float height;
	@Column(name = "weight")
	private Float weight;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
