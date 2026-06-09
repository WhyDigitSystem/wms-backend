package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

private Long id;
	
	private String country;
	private String currency;
    private String subCurrency;
    private String currencySymbol;
    private Long orgId;
    private boolean active;
	private String createdBy;
	private boolean cancel;
}
