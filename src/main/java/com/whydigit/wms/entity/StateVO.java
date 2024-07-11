package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	
	@Column(name="statecode")
	private String statecode;
	@Column(name="state")
	private String statename;
	@Column(name="country")
    private String country;
	@Column(name="region")
    private String region;
	@Column(name="stateno")
    private int statenumber;
	@Column(name="active")
    private boolean active;
	@Column(name="userid")
    private String userid;
    @Column(unique = true)
	private String dupchk;
    @Column(name="createdby")
    private String createdby;
    @Column(name="modifiedby")
	private String updatedby;
    @Column(name="orgid")
	private Long orgId;
    @Column(name="cancel")
	private boolean cancel;

    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
