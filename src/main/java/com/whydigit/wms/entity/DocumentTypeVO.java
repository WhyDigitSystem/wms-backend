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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="m_documenttype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypegen")
	@SequenceGenerator(name = "documenttypegen", sequenceName = "documenttypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypeid")
	private Long id;
	
	@Column(name = "screencode")
	private String screenCode;
	
	@Column(name = "screenname")
	private String screenName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "doccode")
	private String docCode;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedby")
	private String updatedBy;
	
	@Column(name="orgid")
	private Long orgId;
	
	private boolean active;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "documentTypeVO", cascade = CascadeType.ALL)
	private List<DocumentTypeDetailsVO> documentTypeDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }
	
}
