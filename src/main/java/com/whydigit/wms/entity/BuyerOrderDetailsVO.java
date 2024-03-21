package com.whydigit.wms.entity;

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
	@SequenceGenerator(name = "buyerorderdetailsgen", sequenceName = "buyerorderdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerorderdetailsid")
	private Long id;

	@Column(name = "partno")
	private String partno;

	@Column(name = "partdesc")
	private String partdesc;

	@Column(name = "qty")
	private String qty;

	@Column(name = "batchno")
	private String batchno;

	@Column(name = "availqty")
	private String availqty;

	@Column(name = "sku")
	private String sku;

	@Column(name = "remarks")
	private String remarks;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "buyer_order_id")
	private BuyerOrderVO buyerOrderVO;
}
