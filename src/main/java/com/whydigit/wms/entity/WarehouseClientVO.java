package com.whydigit.wms.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "warehouseclient")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseClientVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouseclientgen")
	@SequenceGenerator(name = "warehouseclientseq", sequenceName = "warehouseclientseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouseclientid")
	private Long id;

	@Column(name = "client",length =150)
	private String client;
	@Column(name = "clientcode",length =25)
	private String clientCode;
	@Column(name = "active")
	private boolean active;
	private boolean cancel;
	@Column(name="orgid")
	private Long orgId;
//	@Column(name = "userid")
//	private String userid;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "warehouseid")
	private WarehouseVO warehouseVO;

//	
//	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}