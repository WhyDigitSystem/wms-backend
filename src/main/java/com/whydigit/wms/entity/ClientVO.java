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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientgen")
	@SequenceGenerator(name = "clientgen", sequenceName = "clientseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clientid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "client")
	private String client; // caps
	@Column(name = "clientcode")
	private String clientCode; // caps
	@Column(name = "clienttype")
	private String clientType;
	@Column(name = "fiofife")
	private int fifofife;
	
	private boolean active;
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
	

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "customerid")
	private CustomerVO customerVO;

}
