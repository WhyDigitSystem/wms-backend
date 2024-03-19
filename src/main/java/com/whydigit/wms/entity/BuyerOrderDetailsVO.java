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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "buyerorderdetailsgen")
	@SequenceGenerator(name = "buyerorderdetailsgen",sequenceName = "buyerorderdetailsVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="buyerorderdetailsid")
	private Long id;
	private String partno;
	private String partdesc;
	private String qty;
	private String batchno;
	private String availqty;
	private String sku;
	private String remarks;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "buyer_order_id")
	private BuyerOrderVO buyerOrderVO;
}
