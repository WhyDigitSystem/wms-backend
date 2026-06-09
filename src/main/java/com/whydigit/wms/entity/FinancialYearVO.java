package com.whydigit.wms.entity;

import java.time.LocalDate;

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
@Table(name="financialyear")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financialyearegen")
	@SequenceGenerator(name = "financialyearegen", sequenceName = "financialyearseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "finyearid")
	private Long id;
	@Column(name ="financialyearid")
	private Long finYearId;
	@Column(name="finyear")
	private int finYear;
	@Column(name="finyearidentifier",length =10)
	private String finYearIdentifier;
	@Column(name="startdate")
	private LocalDate startDate;
	@Column(name="enddate")
	private LocalDate endDate;
	@Column(name="currentfinyear")
	private boolean currentFinYear;
	@Column(name="closed")
	private boolean closed;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="createdby",length =25)
	private String createdBy;
	@Column(name="modifiedby",length =25)
	private String updatedBy;
	private boolean active;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
