package com.whydigit.wms.entity;

import java.math.BigDecimal;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dekittingparent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeKittingParentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dekittingparentgen")
	@SequenceGenerator(name = "dekittingparentgen", sequenceName = "dekittingparentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dekittingparentid")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "bin")
	private String bin;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "lotNo")
	private String lotNo;
	@Column(name = "sku")
	private String sku;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "qty")
	private int qty;
	@Column(name = "unitrate")
	private BigDecimal unitRate;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "status")
	private String status="R";
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "clientcode")
	private String clientCode;
	@Column(name = "core")
	private String core;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "pckey")
	private String pcKey;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "stokcdate")
	private LocalDate stockDate;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "dekittingid")
	private DeKittingVO deKittingVO;
}
