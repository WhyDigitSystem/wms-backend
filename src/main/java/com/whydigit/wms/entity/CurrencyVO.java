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
	
	@Column(name="userid", length = 30)
	private String userid;
	
	@Column(name="country", length = 30)
	private String country;
	
	@Column(name="currency", length = 30)
	private String currency;
	
	@Column(name="subcurrency", length = 30)
    private String subcurrency;
	
	@Column(name="currencysymbol", length = 30)
    private String currencysymbol;
    
	@Column(name="orgid")
    private Long orgId;
	
	@Column(name="active")
    private boolean active;
	
	@Column(unique = true)
	private String dupchk;

	@Column(name="createdby", length = 30)
	private String createdby;
	
	@Column(name="modifiedby", length = 30)
	private String updatedby;
	
	@Column(name="cancel")
	private boolean cancel;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}