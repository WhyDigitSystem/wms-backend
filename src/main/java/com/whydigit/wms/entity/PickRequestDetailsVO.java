package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String serialno;
	private String partcode;
	private String partdescripition;
	private String sku;
	private String location;
	private String batchno;
	private String lotno;
	private String orderqty;
	private String avlqty;
	private String pickqty;
	private String runningqty;
	private String pickqtyperlocation;
	private String remainingqty;
	private String weight;
	private String rate;
	private String tax;
	private String amount;
	private String remarks;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "pick_request_id")
	private PickRequestVO pickRequestVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
