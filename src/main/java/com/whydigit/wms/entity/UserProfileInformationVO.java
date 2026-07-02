package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "userprofileinformation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileInformationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userprofileinformationgen")
	@SequenceGenerator(name = "userprofileinformationgen", sequenceName = "userprofileinformationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "userprofileinformationid")
	private Long id;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phonenumber")
	private Long phoneNumber;
	
	@Column(name = "companyname")
	private String companyName;

	@Column(name = "address")
	private String address;
	
	@Column(name = "bioinformation")
	private String bioInformation;
	
	@Lob
	@Column(name = "userprofileimage", columnDefinition = "LONGBLOB")
	private byte[] userProfileImage;
	

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifyby", length = 25)
	private String updatedBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel  =false;

	@Column(name = "userid")
	private Long userId;
	


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
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}
