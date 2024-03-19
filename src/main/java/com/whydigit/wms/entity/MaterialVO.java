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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "materialgen")
	@SequenceGenerator(name = "materialgen",sequenceName = "materialVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="materialid")
	private Long id;
	private String itemtype;
	private String partno;
	private String partdesc;
	private String custpartno;
	private String groupname;
	private String barcode;
	private String stylecode;
	private String basesku;
	private String adddesc;
	private String purchaseunit;
	private String storageunit;
	private String fixedcapacrosslocn;
	private String fsn;
	private String saleunit;
	private String type;
	private String serialnoflag;
	private String sku;
	private String skuqty;
	private String ssku;
	private String sskuqty;
	private String zonetype;
	private String weightofskuanduom;
	private String hsncode;
	private String parentchildkey;
	private String controlbranch;
	private String criticalstocklevel;
	private String criticalstock;
	private String bchk;
	private String status;
	private Long orgid;
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
	private String modifiedby;
	private Float length;
	private Float breadth;
	private Float height;
	private Float weight;
	@Column(unique = true)
	private String dupchk;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
