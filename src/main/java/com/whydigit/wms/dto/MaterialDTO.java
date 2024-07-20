package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {

	private Long id;
	private String itemType;
	private String partno;
	private String partDesc;
	private String custPartno;
	private String groupName;
	private String barcode;
	private String styleCode;
	private String baseSku;
	private String purchaseUnit;
	private String storageUnit;
	private String fsn;
	private String saleUnit;
	private String type;
	private String sku;
	private String skuQty;
	private String ssku;
	private String sskuQty;
	private String weightOfSkuAndUom;
	private String hsnCode;
	private String parentChildKey;
	private String cbranch;
	private String criticalStockLevel;
	private String status;
	private Long orgId;
	private String customer;
	private String client;
	private String warehouse;
	private String branch;
	private String branchCode;
	private String palletQty;
	private boolean active;
	private String createdBy;
	private Float length;
	private Float breadth;
	private Float height;
	private Float weight;
}
