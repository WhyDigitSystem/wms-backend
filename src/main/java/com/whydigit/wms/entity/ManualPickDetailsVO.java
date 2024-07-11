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
	@SequenceGenerator(name = "manualpickdetailsgen", sequenceName = "manualpickdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "manualpickdetailsid")
	private Long id;

	@Column(name = "serialno")
	private String serialno;
	@Column(name = "partcode")
	private String partcode;
	@Column(name = "partdescripition")
	private String partdescripition;
	@Column(name = "batchno")
	private String batchno;
	@Column(name = "grnno")
	private String grnno;
	@Column(name = "location")
	private String location;
	@Column(name = "lotno")
	private String lotno;
	@Column(name = "sku")
	private String sku;
	@Column(name = "orderqty")
	private int orderqty;
	@Column(name = "avlqty")
	private int avlqty;
	@Column(name = "pickqty")
	private int pickqty;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private String amount;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremark")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "screencode")
	private String screencode;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "manualpickid")
	private ManualPickVO manualPickVO;
}
