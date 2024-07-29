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
@Table(name="m_documenttypedetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypedetailsgen")
	@SequenceGenerator(name = "documenttypedetailsgen", sequenceName = "documenttypedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypedetailsid")
	private Long id;
	
	@Column(name="client")
	private String client;
	
	@Column(name="clientcode")
	private String clientCode;
	
	@Column(name="screencode")
	private String screenCode;
	
	@Column(name="screenname")
	private String screenName;
	
	@Column(name="doccode")
	private String docCode;
	
	@Column(name="orgid")
	private Long orgId;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "documenttypeid")
	private DocumentTypeVO documentTypeVO;

}