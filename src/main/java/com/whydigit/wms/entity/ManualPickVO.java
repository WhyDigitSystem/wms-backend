package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualPickVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String transactiontype;
	private String docid;
	private LocalDate docdate;
	private String buyerrefno;
	private String buyerorderno;
	private LocalDate buyerrefdate;
	private String invoiceno;
	private String clientname;
	private String shortname;
	private String clientaddress;
	private String customername;
	private String customeraddress;
	private String noodboxes;
	private String duedays;
	private String outtime;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String screencode;

	@JsonManagedReference
	@OneToMany(mappedBy = "manualPickVO")
	private List<ManualPickDetailsVO> manualPickDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
