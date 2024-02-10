package com.whydigit.wms.entity;

import javax.persistence.Column;
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
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long unitid;
	private String userid;
	@Column(unique = true)
	private String uom;
	private String unitname;
	private String unittype;
	private boolean active;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
