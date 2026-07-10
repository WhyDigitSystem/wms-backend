package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_documenttypedetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_documenttypedetailsgen")
	@SequenceGenerator(name = "m_documenttypedetailsgen", sequenceName = "m_documenttypedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "m_documenttypedetailsid")
	private Long id;

	@Column(name = "client", length = 150)
	private String client;

	@Column(name = "clientcode", length = 25)
	private String clientCode;

	@Column(name = "screencode", length = 10)
	private String screenCode;

	@Column(name = "screenname", length = 25)
	private String screenName;

	@Column(name = "doccode", length = 25)
	private String docCode;

	@Column(name = "orgid")
	private Long orgId;

//	@JsonBackReference
//	@ManyToOne
//	@JoinColumn(name = "m_documenttypeid")
//	private DocumentTypeVO documentTypeVO;

}
