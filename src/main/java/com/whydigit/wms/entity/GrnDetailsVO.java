package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long grndetailsid;
	private String qrcode;
	private String lrnohawbno;
	private String invoiceno;
	private LocalDate invoicedate;
	private String partno;
	private String partdescription;
	private String sku;
	private String invqty;
	private String recqty;
	private String shortqty;
	private String damageqty;
	private String grnqty;
	private String substockqty;
	private String batchpalletqty;
	private String batchqty;
	private String expdate;
	private String pallteqty;
	private String noofpallet;
	private String pkgs;
	private String weight;
	private String address;
	private String billofenrtyno;
	private String containerno;
	private String fifoflag;
	private String flag;
	private String source;
	private String screen;
	private String stockdate;
	private String vas;
	private String vehicleno;
	private String vehicledetails;
	private String vesselno;

	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private String cancel;
	private String userid;
	private String cancelremark;
	private String active;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "grn_id")
	GrnVO grnVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
