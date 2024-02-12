package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cell_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CellTypeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long celltypeid;
	private String celltype;
	private String userid;
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
