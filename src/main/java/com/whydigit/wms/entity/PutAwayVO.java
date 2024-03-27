package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putaway")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;                                                                                           
	private String branchcode;
	private String branch;
	private String screencode;
	private String client;
	private String customer;

	@JsonManagedReference
	@OneToMany(mappedBy = "putAwayVO", cascade = CascadeType.ALL)
	private List<PutAwayDetailsVO> putAwayDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
