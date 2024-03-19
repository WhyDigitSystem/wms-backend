package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayDTO {
	private LocalDate docdate;
	private String grnno;
	private LocalDate grndate;
	private String entryno;
	private String core;
	private String suppliershortname;
	private String supplier;
	private String modeodshipment;
	private String carrier;
	private String locationtype;
	private String status;
	private String lotno;
	private String enteredperson;
	List<PutAwayDetailsDTO> putAwayDetailsDTO;
}
