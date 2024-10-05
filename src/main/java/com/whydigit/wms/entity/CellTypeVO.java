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
@Table(name = "celltype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellTypeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "celltypegen")
	@SequenceGenerator(name = "celltypegen",sequenceName = "celltypeseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="celltypeid")
	private Long id;
	
	@Column(name="celltype",length =25)
	private String cellType;
	@Column(name="active")
	private boolean active;
	@Column(name="createdby",length =25)
	private String createdBy;
	@Column(name="modifiedby",length =25)
	private String updatedBy;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="cancel")
	private boolean cancel;
	
	
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
