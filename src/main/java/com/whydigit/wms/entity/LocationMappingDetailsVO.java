package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locationmappingdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMappingDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationmappingdetailsgen")
	@SequenceGenerator(name = "locationmappingdetailsgen", sequenceName = "locationmappingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "locationmappingdetailsid")
	private Long id;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "rowno",length =25)
	private String rowNo;
	@Column(name = "levelno",length =25)
	private String levelNo;
	@Column(name = "bincategory",length =25)
	private String binCategory;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "binstatus",length =25)
	private String binStatus;
	@Column(name = "binseq",length =25)
	private String binSeq;
	@Column(name = "core",length =25)
	private String core;
	private boolean active;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "locationmappingid")
	private LocationMappingVO locationMappingVO;

}
