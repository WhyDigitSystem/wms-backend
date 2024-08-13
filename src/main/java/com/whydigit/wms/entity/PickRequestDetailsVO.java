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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickrequestdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pickrequestdetailsgen")
	@SequenceGenerator(name = "pickrequestdetailsgen", sequenceName = "pickrequestdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestdetailsid")
	private Long id;

	@Column(name = "partcode")
	private String partCode;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "sku")
	private String sku;
	@Column(name = "core")
	private String core;
	@Column(name = "location")
	private String location;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "orderqty")
	private int orderQty;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "runningqty")
	private int runningQty;
	@Column(name = "pickqtyperlocation")
	private String pickQtyPerLocation;
	@Column(name = "remainingqty")
	private String remainingQty;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "tax")
	private String tax;
	@Column(name = "amount")
	private String amount;
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "clientcode")
	private String clientCode;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "pockey")
	private String pcKey;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "stokcdate")
	private LocalDate stockDate;
	
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "pickrequestid")
	private PickRequestVO pickRequestVO;

}