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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unitgen")
	@SequenceGenerator(name = "unitgen", sequenceName = "unitVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "unitid")
	private Long id;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "uom", length = 30)
	private String uom;

	@Column(name = "unitname", length = 30)
	private String unitname;

	@Column(name = "unittype", length = 30)
	private String unittype;

	@Column(name = "active")
	private boolean active;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "cancel")
	private boolean cancel;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
