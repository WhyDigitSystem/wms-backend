package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "currencygen")
	@SequenceGenerator(name = "currencygen",sequenceName = "currencyVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="currencyid")
	private Long id;
	private String userid;
	private String country;
	private String currency;
    private String subcurrency;
    private String currencysymbol;
    private Long orgid;
    private boolean active;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private boolean cancel;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}