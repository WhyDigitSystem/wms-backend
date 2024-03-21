package com.whydigit.wms.entity;

import java.time.LocalDate;

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
@Table(name = "gatepassindetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePassLrNoDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String sno;
	private String orgid;
	private String lrnohaw;
	private String invoiceno;
	private LocalDate invoicedate;
	private String partno;
	private String partcode;
	private String partdescription;
	private String batchno;
	private String unit;
	private int invqty;
	private int recqty;
	private int shortqty;
	private int damageqty;
	private int grnqty;
	private String subunit;
	private int substockshortqty;
	private int grnpiecesqty;
	private String weight;
	private String rate;
	private String amount;
	private String remarks;

	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "gatepassid")
    private GatePassInVO gatePassInVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
