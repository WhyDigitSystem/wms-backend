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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliverychallandetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallandetailsgen")
	@SequenceGenerator(name = "deliverychallandetailsgen", sequenceName = "deliverychallandetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychallandetailsid")
	private Long id;
	
	@Column(name = "pickrequestno",length =25)
	private String pickRequestNo;
	@Column(name = "prdate")
	private LocalDate prDate;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdescription",length =150)
	private String partDescription;
	@Column(name = "outboundbin",length =25)
	private String outBoundBin;
	@Column(name = "shippedqty")
	private int shippedQty;
	@Column(name = "unitrate")
	private int unitRate;
	@Column(name = "skuvalue")
	private int skuValue;
	@Column(name = "discount")
	private BigDecimal discount;
	@Column(name = "tax")
	private int tax;
	@Column(name = "gsttax")
	private int gstTax;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "sgst")
	private BigDecimal sgst;
	@Column(name = "cgst")
	private BigDecimal cgst;
	@Column(name = "igst")
	private BigDecimal igst;
	@Column(name = "totalgst")
	private BigDecimal totalGst;
	@Column(name = "billamount")
	private BigDecimal billAmount;
	@Column(name = "remarks",length =150)
	private String remarks;
//	@Column(name = "qcflag")
//	private String qcFlag;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "deliverychallanid")
	private DeliveryChallanVO deliveryChallanVO;

}
