package com.whydigit.wms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouselocation")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseLocationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouselocationgen")
	@SequenceGenerator(name = "warehouselocationgen", sequenceName = "warehouselocationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouselocationid")
	private Long id;

	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "rowno",length =25)
	private String rowNo;
	@Column(name = "level",length =25)
	private String level;
	@Column(name = "cellform",length =25)
	private String cellFrom;
	@Column(name = "cellto",length =25)
	private String cellTo;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@OneToMany(mappedBy = "warehouseLocationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<WarehouseLocationDetailsVO> warehouseLocationDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

}
