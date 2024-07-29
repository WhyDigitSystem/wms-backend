package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="screenname")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenNamesVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screennamegen")
	@SequenceGenerator(name = "screennamegen", sequenceName = "screennameseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "screennameid")
	private Long id;
	
	@Column(name="screenname")
	private String screenName;
	
	@Column(name="screencode")
	private  String screenCode;
	
	private boolean active;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy;
	
	
	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

}