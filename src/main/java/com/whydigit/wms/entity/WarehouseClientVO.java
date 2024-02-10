package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseClientVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long whclientid;
	private String client;
	private String clientcode;
	private boolean active;
	private boolean userid;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}