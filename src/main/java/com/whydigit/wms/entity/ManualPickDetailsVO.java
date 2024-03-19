package com.whydigit.wms.entity;

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
@Table(name = "manualpickdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualPickDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String serialno;
	private String partcode;
	private String partdescripition;
	private String batchno;
	private String grnno;
	private String location;
	private String lotno;
	private String sku;
	private String orderqty;
	private String avlqty;
	private String pickqty;
	private String weight;
	private String rate;
	private String amount;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String screencode;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "manualpickid", referencedColumnName = "id")
	private ManualPickVO manualPickVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
