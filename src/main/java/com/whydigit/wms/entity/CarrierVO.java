package com.whydigit.wms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long carrierid;
	private String carriername;
	private String shortname;
	private String shipmentmode;
	private String controlbranch;
	@Column(unique = true)
	private String dupchk;
	private String company;
	private boolean active;
	private long userid;
	private String cancel;
	private String cancelremarks;
	private String createdby;
	private String updatedby;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "carrierVO", cascade = CascadeType.ALL)
	private List<CarrierDetailsVO> carrierDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
