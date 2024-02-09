package com.whydigit.wms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouse_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long whlocationid;
	private String location;
}