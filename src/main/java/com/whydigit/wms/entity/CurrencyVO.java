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
	@SequenceGenerator(name = "currencygen",sequenceName = "currencyseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="currencyid")
	private Long id;
	
	@Column(name="userid")
	private String userid;
	@Column(name="country")
	private String country;
	@Column(name="currency")
	private String currency;
	@Column(name="subcurrency")
    private String subcurrency;
	@Column(name="currencysymbol")
    private String currencysymbol;
	@Column(name="orgid")
    private Long orgId;
	@Column(name="active")
    private boolean active;
	@Column(unique = true)
	private String dupchk;
	@Column(name="createdby")
	private String createdby;
	@Column(name="modifiedby")
	private String updatedby;
	@Column(name="cancel")
	private boolean cancel;
	
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}