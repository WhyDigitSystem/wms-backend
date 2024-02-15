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
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clientid;
	private String company;
	private String client; // caps
	private String clientcode; // caps
	private String clienttype;
	private int fifofife;
	@Column(unique = true)
	private String dupchk;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "customerid")
	private CustomerVO customerVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}