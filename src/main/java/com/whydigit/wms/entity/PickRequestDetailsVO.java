package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickrequestdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pickrequestdetailsgen")
	@SequenceGenerator(name = "pickrequestdetailsgen", sequenceName = "pickrequestdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestdetailsid")
	private Long id;

	@Column(name = "serialno")
	private String serialno;
	@Column(name = "partcode")
	private String partcode;
	@Column(name = "partdesc")
	private String partdesc;
	@Column(name = "sku")
	private String sku;
	@Column(name = "location")
	private String location;
	@Column(name = "batchno")
	private String batchno;
	@Column(name = "lotno")
	private String lotno;
	@Column(name = "orderqty")
	private int orderqty;
	@Column(name = "avlqty")
	private int avlqty;
	@Column(name = "pickqty")
	private int pickqty;
	@Column(name = "runningqty")
	private int runningqty;
	@Column(name = "picktyperlocation")
	private String pickqtyperlocation;
	@Column(name = "remainingqty")
	private String remainingqty;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "tax")
	private String tax;
	@Column(name = "amount")
	private String amount;
	@Column(name = "remarks")
	private String remarks;
	
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "pickrequestid")
	private PickRequestVO pickRequestVO;

}
