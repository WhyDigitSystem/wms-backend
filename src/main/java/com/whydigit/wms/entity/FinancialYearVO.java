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
	private int finyr;
	private int finyridentifier;
	private LocalDate startdate;
	private LocalDate enddate;
	private boolean currentfinyr;
	private boolean closed;
	private String company;
	private String dupchk;
	private String userid;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
