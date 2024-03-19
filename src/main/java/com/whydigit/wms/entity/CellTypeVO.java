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
	@SequenceGenerator(name = "celltypegen",sequenceName = "celltypeVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="celltypeid")
	private Long id;
	private String celltype;
	private String userid;
	private boolean active;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private Long orgid;
	private boolean cancel;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
