package com.whydigit.wms.entity;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putawaydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String partno;
	private String batch;
	private String partdescripition;
	private String sku;
	private String invqty;
	private String recqty;
	private String putawayqty;
	private String putawaypiecesqty;
	private String location;
	private String weight;
	private String rate;
	private String amount;
	private String remarks;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "putawayid")
	private PutAwayVO putAwayVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
