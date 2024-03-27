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
@Data
@Table(name = "warehouseclient")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseClientVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouseclientgen")
	@SequenceGenerator(name = "warehouseclient", sequenceName = "warehouseclientVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "warehouseclientid")
	private Long id;

	@Column(name = "client")
	private String client;

	@Column(name = "clientcode")
	private String clientcode;

	@Column(name = "active")
	private boolean active;

	@Column(name = "userid")
	private String userid;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "warehouseid")
	private WarehouseVO warehouseVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}