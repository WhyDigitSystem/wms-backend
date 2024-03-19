package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickrequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String transcationtype;
	private String docid;
	private String buyerrefno;
	private LocalDate docdate;
	private String shipmentmethod;
	private String buyerorderno;
	private String buyersreference;
	private String invoiceno;
	private String clientname;
	private String clientshortname;
	private String clientaddress;
	private String dispatch;
	private String customername;
	private String customeraddress;
	private String duedays;
	private String noofboxes;
	private String pickorder;
	private String outtime;
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

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pickRequestVO")
	private List<PickRequestDetailsVO> pickRequestDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
