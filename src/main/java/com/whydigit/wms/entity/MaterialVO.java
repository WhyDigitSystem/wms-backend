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

	@Column(name = "itemtype")
	private String itemtype;

	@Column(name = "partno")
	private String partno;

	@Column(name = "partdesc")
	private String partdesc;

	@Column(name = "custpartno")
	private String custpartno;

	@Column(name = "groupname")
	private String groupname;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "stylecode")
	private String stylecode;

	@Column(name = "basesku")
	private String basesku;

	@Column(name = "adddesc")
	private String adddesc;

	@Column(name = "purchaseunit")
	private String purchaseunit;

	@Column(name = "storageunit")
	private String storageunit;

	@Column(name = "fixedcapacrosslocn")
	private String fixedcapacrosslocn;

	@Column(name = "fsn")
	private String fsn;

	@Column(name = "saleunit")
	private String saleunit;

	@Column(name = "type")
	private String type;

	@Column(name = "serialnoflag")
	private String serialnoflag;

	@Column(name = "sku")
	private String sku;

	@Column(name = "skuqty")
	private String skuqty;

	@Column(name = "ssku")
	private String ssku;

	@Column(name = "sskuqty")
	private String sskuqty;

	@Column(name = "zonetype")
	private String zonetype;

	@Column(name = "weightofskuanduom")
	private String weightofskuanduom;

	@Column(name = "hsncode")
	private String hsncode;

	@Column(name = "parentchildkey")
	private String parentchildkey;

	@Column(name = "controlbranch")
	private String controlbranch;

	@Column(name = "criticalstocklevel")
	private String criticalstocklevel;

	@Column(name = "criticalstock")
	private String criticalstock;

	@Column(name = "bchk")
	private String bchk;

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

	@Column(name = "branchname")
	private String branchname;

	@Column(name = "branchcode")
	private String branchcode;

	@Column(name = "userid")
	private String userid;

	@Column(name = "palletqty")
	private String palletqty;

	@Column(name = "cbranch")
	private String cbranch;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "parentchild")
	private String parentchild;

	@Column(name = "createdby")
	private String createdby;

	@Column(name = "modifiedby")
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
