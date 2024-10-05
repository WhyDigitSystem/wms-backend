package com.whydigit.wms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "useraction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useractiongen")
	@SequenceGenerator(name = "useractiongen", sequenceName = "useractionseq", initialValue = 1000000001, allocationSize = 1)
	private Long actionId;
	
	private String userName;
	private Long usersId;
	private String actionType;
	private Date actionDate;
	
	@PrePersist
	public void onSave() {
		Date currentDate = new Date();
		this.actionDate = currentDate;
	}
	
}
