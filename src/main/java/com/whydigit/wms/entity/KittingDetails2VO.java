package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kittingdetails2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KittingDetails2VO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kittingdetails2gen")
	@SequenceGenerator(name = "kittingdetails2gen", sequenceName = "kittingdetails2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "kitting2id")
	private Long id;
	@Column(name="ppartno")
	private String pPartNo;
	@Column(name="ppartdescription")
	private String pPartDescription;
	@Column(name="pbatchno")
	private String PBatchNo;
	@Column(name="plotno")
	private String pLotNo;
	@Column(name="pgrnno")
	private String pGrnNo;
	@Column(name="pgrndate")
	private LocalDate pGrnDate;
	@Column(name="pqcflag")
	private boolean qQcflag;	
	@Column(name="pexpdate")
	private LocalDate pExpDate;
	@Column(name="pqty")
	private int pQty;
	
	@ManyToOne
	@JoinColumn(name ="kittingid")
	@JsonBackReference
	private KittingVO kittingVO;

	
}
