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
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unitgen")
	@SequenceGenerator(name = "unitgen", sequenceName = "unitseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "unitid")
	private Long id;

	@Column(name = "unitname",length =25)
	private String unitName;
	@Column(name = "unittype",length =25)
	private String unitType;
	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "uom",length =25)
	private String uom;

	@Column(name = "cancel")
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
