package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stategen")
	@SequenceGenerator(name = "stategen",sequenceName = "stateseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="stateid")
	private Long id;
	
	@Column(name="statecode",length =25)
	private String stateCode;
	@Column(name="state",length =25)
	private String stateName;
	@Column(name="country",length =25)
    private String country;
	@Column(name="region",length =25)
    private String region;
	@Column(name="statenumber",length =25)
	 private String stateNumber;
	@Column(name="active")
    private boolean active;
//	@Column(name="userid")
//    private String userId;
    @Column(name="createdby",length =25)
    private String createdBy;
    @Column(name="modifiedby",length =25)
	private String updatedBy;
    @Column(name="orgid")
	private Long orgId;
    @Column(name="cancel")
	private boolean cancel;

    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
