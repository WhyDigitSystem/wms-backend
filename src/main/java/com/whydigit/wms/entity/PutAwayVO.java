package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putaway")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutAwayVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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

	@JsonManagedReference
	@OneToMany(mappedBy = "putAwayVO", cascade = CascadeType.ALL)
	private List<PutAwayDetailsVO> putAwayDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}