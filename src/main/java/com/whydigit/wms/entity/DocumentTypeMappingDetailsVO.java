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
@Table(name="m_documenttypemappingdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeMappingDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypemappingdetailsgen")
	@SequenceGenerator(name = "documenttypemappingdetailsgen", sequenceName = "documenttypemappingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypemappingdetailsid")
	private Long id;
	
	@Column(name = "screencode",length =10)
	private String screenCode;
	
	@Column(name = "screenname",length =25)
	private String screenName;
	
	@Column(name = "client",length =150)
	private String client;
	
	@Column(name = "clientcode",length =25)
	private String clientCode;
	
	@Column(name = "doccode",length =25)
	private String docCode;
	
	@Column(name = "branch",length =25)
	private String branch;
	
	@Column(name = "branchcode",length =25)
	private String branchCode;
	
	@Column(name = "prefixfield",length =25)
	private String prefixField;
	
	@Column(name = "finyear",length =10)
	private String finYear;
	
	@Column(name = "finyearidentifier",length =10)
	private String finYearIdentifier;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "concatenation",length =25)
	private String concatenation;
	
	@Column(name="lastno")
	private int lastno=1;
	

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "documenttypemappingid")
	private DocumentTypeMappingVO documentTypeMappingVO;
}
