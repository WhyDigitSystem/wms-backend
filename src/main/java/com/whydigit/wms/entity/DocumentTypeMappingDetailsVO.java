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
	
	@Column(name = "screencode")
	private String screenCode;
	
	@Column(name = "screenname")
	private String screenName;
	
	@Column(name = "client")
	private String client;
	
	@Column(name = "clientcode")
	private String clientCode;
	
	@Column(name = "doccode")
	private String docCode;
	
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "branchcode")
	private String branchCode;
	
	@Column(name = "prefixfield")
	private String prefixField;
	
	@Column(name = "finyear")
	private String finYear;
	
	@Column(name = "finyearidentifier")
	private String finYearIdentifier;
	
	@Column(name = "orgid")
	private String orgId;
	
	@Column(name = "concatenation")
	private String concatenation;
	
	@Column(name="lastno")
	private int lastno=1;
	

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "documenttypemappingid")
	private DocumentTypeMappingVO documentTypeMappingVO;
}
