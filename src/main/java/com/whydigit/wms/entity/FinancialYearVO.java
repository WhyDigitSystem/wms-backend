package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long finyearid;
	private int finyr;
	private int finyridentifier;
	private LocalDate startdate;
	private LocalDate enddate;
	private boolean currentfinyr;
	private boolean closed;
	private String company;
	private String dupchk;
	private String userid;
	
	

}
