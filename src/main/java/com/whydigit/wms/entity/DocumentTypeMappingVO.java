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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="m_documenttypemapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeMappingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypemappinggen")
	@SequenceGenerator(name = "documenttypemappinggen", sequenceName = "documenttypemappingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypemappingid")
	private Long id;
	
	
	private String branch;
	
	@Column(name = "branchcode")
	private String branchCode;
	
	@Column(name = "finyear")
	private String finYear;
	
	@Column(name = "finyearidentifier")
	private String finYearIdentifier;
	
	@Column(name = "orgid")
	private String orgId;
	
	@Column(name="createdby")
	private String createdBy;
	
	@Column(name="modifiedby")
	private String updatedBy;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "documentTypeMappingVO", cascade = CascadeType.ALL)
	private List<DocumentTypeMappingDetailsVO>documentTypeMappingDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
