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
	@SequenceGenerator(name = "pickrequestdetailsgen", sequenceName = "pickrequestdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestdetailsid")
	private Long id;

	@Column(name = "serialno", length = 30)
	private String serialno;

	@Column(name = "partcode", length = 30)
	private String partcode;

	@Column(name = "partdesc", length = 30)
	private String partdesc;

	@Column(name = "sku", length = 30)
	private String sku;

	@Column(name = "location", length = 30)
	private String location;

	@Column(name = "batchno", length = 30)
	private String batchno;

	@Column(name = "lotno", length = 30)
	private String lotno;

	@Column(name = "orderqty")
	private int orderqty;

	@Column(name = "avlqty")
	private int avlqty;

	@Column(name = "pickqty")
	private int pickqty;

	@Column(name = "runningqty")
	private int runningqty;

	@Column(name = "picktyperlocation", length = 30)
	private String pickqtyperlocation;

	@Column(name = "remainingqty")
	private String remainingqty;

	@Column(name = "weight", length = 30)
	private String weight;

	@Column(name = "rate", length = 30)
	private String rate;

	@Column(name = "tax", length = 30)
	private String tax;

	@Column(name = "amount", length = 30)
	private String amount;

	@Column(name = "remarks", length = 30)
	private String remarks;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "pick_request_id")
	private PickRequestVO pickRequestVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
