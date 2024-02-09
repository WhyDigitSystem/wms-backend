package com.whydigit.wms.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;
import com.whydigit.wms.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long warehouseid;
	private String warehouse;
	 @Embedded
		private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
