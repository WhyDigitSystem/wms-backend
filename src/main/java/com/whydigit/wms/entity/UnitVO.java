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
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "unitgen")
	@SequenceGenerator(name = "unitgen",sequenceName = "unitVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="unitid")
	private Long id;
	private String userid;
	private String uom;
	private String unitname;
	private String unittype;
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
