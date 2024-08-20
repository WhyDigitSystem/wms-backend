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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reversepickdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReversePickDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reversepickdetailsgen")
	@SequenceGenerator(name = "reversepickdetailsgen", sequenceName = "reversepickdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "reversepickdetailsid")
	private Long id;
	@Column(name = "partcode")
	private String partCode;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "sku")
	private String sku;
	@Column(name = "location")
	private String location;
	@Column(name = "tolocation")
	private String toLocation;
	@Column(name = "orderqty")
	private int orderQty;
	@Column(name = "pickedqtyperlocation")
	private int pickedQtyPerLocation;
	@Column(name = "revisedqtyperlocation")
	private int revisedQtyPerLocation;
	@Column(name = "weight")
	private int weight;
	@Column(name = "pgroup")
	private int pGroup;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "rate")
	private int rate;
	@Column(name = "tax")
	private int tax;
	@Column(name = "amount")
	private int amount;
	@Column(name = "remarks")
	private String reMarks;

	@ManyToOne
	@JoinColumn(name = "reversepickid")
	@JsonBackReference
	private ReversePickVO reversePickVO;

}
