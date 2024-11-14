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

	@Column(name = "itemtype",length =25)
	private String itemType;
	@Column(name = "partno",length =25)
	private String partno;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "custpartno",length =25)
	private String custPartno;
	@Column(name = "groupname",length =25)
	private String groupName;
	@Column(name = "barcode",length =25)
	private String barcode;
	@Column(name = "stylecode",length =25)
	private String styleCode;
	@Column(name = "basesku",length =25)
	private String baseSku;
	@Column(name = "purchaseunit",length =25)
	private String purchaseUnit;
	@Column(name = "storageunit",length =25)
	private String storageUnit;
	@Column(name = "fsn",length =25)
	private String fsn;
	@Column(name = "salenit",length =25)
	private String saleUnit;
	@Column(name = "type",length =25)
	private String type;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "skuty",length =25)
	private String skuQty;
	@Column(name = "ssku",length =25)
	private String ssku;
	@Column(name = "sskuqty",length =25)
	private String sskuQty;
	@Column(name = "weightofskuanduom",length =25)
	private String weightofSkuAndUom;
	@Column(name = "hsncode",length =25)
	private String hsnCode;
	@Column(name = "parentchildkey",length =25)
	private String parentChildKey;
	@Column(name = "cbranch",length =25)
	private String cbranch;
	@Column(name = "criticalstocklevel",length =25)
	private String criticalStockLevel;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "palletqty",length =25)
	private String palletQty;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "length")
	private Float length;
	@Column(name = "breadth")
	private Float breadth;
	@Column(name = "height")
	private Float height;
	@Column(name = "weight")
	private Float weight;
	@Column(name = "movingtype",length =25)
	private String movingType;
	@Column(name = "racklevel",length =25)
	private String rackLevel;
	@Column(name = "lowqty")
	private int lowQty;
	
	

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
