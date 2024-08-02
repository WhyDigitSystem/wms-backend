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
	
	@Column(name="celltype")
	private String cellType;
	@Column(name="active")
	private boolean active;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="cancel")
	private boolean cancel;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
