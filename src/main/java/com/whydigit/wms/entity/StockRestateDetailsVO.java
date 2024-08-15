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
@Table(name = "stockrestatedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRestateDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockrestatedetailsgen")
	@SequenceGenerator(name = "stockrestatedetailsgen", sequenceName = "stockrestatedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockrestatedetailsid")
	private Long id;
	
	@Column(name = "bin")
	private String bin;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "sku")
	private String sku;
	@Column(name = "tobin")
	private String toBin;
	@Column(name = "tobinclass")
	private String toBinClass;
	@Column(name = "tobintype")
	private String toBinType;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainqty")
	private int remainQty;
	@Column(name = "noted")
	private String noted;
	@Column(name = "qcflags")
	private boolean qcFlags;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "status")
	private String status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "stockrestateid")
	private StockRestateVO stockRestateVO;
	
	
}
