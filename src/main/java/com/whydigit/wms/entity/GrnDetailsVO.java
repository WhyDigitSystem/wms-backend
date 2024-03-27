package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String qrcode;
	private String lrnohawbno;
	private String invoiceno;
	private LocalDate invoicedate;
	private String partno;
	private String partdesc;
	private String locationtype;
	private String rate;
	private String amount;
	private String sku;
	private int invqty;
	private int recqty;
	private int shortqty;
	private int damageqty;
	private int substockqty;
	private int batchqty;
	private int palletqty;
	private String noofpallet;
	private String pkgs;
	private String weight;
	private String warehouse;
	private String batchno;
	private LocalDate batchdt;
	private String qcflag;
	private String shipmentno;
	private int sqty;
	private String cancelremark;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "grn_id") // Specify the name of the foreign key column
	private GrnVO grnVO;

}