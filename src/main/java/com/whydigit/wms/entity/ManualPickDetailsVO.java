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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manualpickdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualPickDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manualpickdetailsgen")
	@SequenceGenerator(name = "manualpickdetailsgen", sequenceName = "manualpickdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "manualpickdetailsid")
	private Long id;

	@Column(name = "serialno", length = 30)
	private String serialno;

	@Column(name = "partcode", length = 30)
	private String partcode;

	@Column(name = "partdescripition", length = 30)
	private String partdescripition;

	@Column(name = "batchno", length = 30)
	private String batchno;

	@Column(name = "grnno", length = 30)
	private String grnno;

	@Column(name = "location", length = 30)
	private String location;

	@Column(name = "lotno", length = 30)
	private String lotno;

	@Column(name = "sku", length = 30)
	private String sku;

	@Column(name = "orderqty")
	private int orderqty;

	@Column(name = "avlqty")
	private int avlqty;

	@Column(name = "pickqty")
	private int pickqty;

	@Column(name = "weight", length = 30)
	private String weight;

	@Column(name = "rate", length = 30)
	private String rate;

	@Column(name = "amount", length = 30)
	private String amount;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "company", length = 30)
	private String company;

	@Column(name = "cancel", length = 30)
	private boolean cancel;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "cancelremark", length = 30)
	private String cancelremark;

	@Column(name = "active")
	private boolean active;

	@Column(name = "screencode", length = 30)
	private String screencode;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "manualpickid")
	private ManualPickVO manualPickVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
