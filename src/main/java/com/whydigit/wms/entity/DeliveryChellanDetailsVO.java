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
@Table(name = "deliverychellandetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChellanDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychellandetailsgen")
	@SequenceGenerator(name = "deliverychellandetailsgen", sequenceName = "deliverychellandetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychellandetailsid")
	private Long id;
	
	@Column(name = "pickrequestno")
	private String pickRequestNo;
	@Column(name = "prdate")
	private LocalDate prDate;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "outboundlocation")
	private String outBoundLocation;
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
	@Column(name = "gattax")
	private int gatTax;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "sgst")
	private BigDecimal sgst;
	@Column(name = "cgst")
	private BigDecimal cgst;
	@Column(name = "totalgst")
	private BigDecimal totalGst;
	@Column(name = "billamount")
	private BigDecimal billAmount;
	@Column(name = "remarks")
	private String remarks;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "deliverychallanid")
	private DeliveryChallanVO deliveryChallanVO;

}
