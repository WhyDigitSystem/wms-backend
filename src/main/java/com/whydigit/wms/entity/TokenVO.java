package com.whydigit.wms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVO {

	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokengen")
//	@SequenceGenerator(name = "tokengen", sequenceName = "tokenseq", initialValue = 1000000001, allocationSize = 1)
	private String id;
	private Date createdDate;
	private Date expDate;
	private Long userId;
	@Transient
	private String token;

	//@Embedded
	//private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
