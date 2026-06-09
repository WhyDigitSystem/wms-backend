package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDetails2DTO {
	private Long id;
	private String ppartNo;
	private String ppartDescription;
	private String psku;
	private String pgrnNo;
	private LocalDate pgrnDate;
	private String pbatchNo;
	private LocalDate pbatchDate;
	private String pbin;
	private String pbinType;
	private String pbinClass;
	private String pcellType;
	private String pcore;
	private String plotNo;
	private String pqcflag;	
	private int pqty;
	private LocalDate pexpDate;
}
