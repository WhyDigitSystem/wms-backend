package com.whydigit.wms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long currencyid;
	private String userid;
	private String country;
	private String currency;
    private String subcurrency;
    private String currencysymbol;
    private boolean active;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}