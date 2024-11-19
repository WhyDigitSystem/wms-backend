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
@Table(name = "buyerorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerOrderDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyerorderdetailsgen")
	@SequenceGenerator(name = "buyerorderdetailsgen", sequenceName = "buyerorderdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerorderdetailsid")
	private Long id;

	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "qty")
	private int qty;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "availqty")
	private int availQty;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "remarks",length =150)
	private String reMarks;
	@Column(name="expdate")
	private LocalDate expDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "buyerorderid")
	private BuyerOrderVO buyerOrderVO;
}
