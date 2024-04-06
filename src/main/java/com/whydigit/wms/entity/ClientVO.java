package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

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
	@SequenceGenerator(name = "clientgen", sequenceName = "clientVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clientid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "client", length = 30)
	private String client; // caps
	@Column(name = "clientcode", length = 30)
	private String clientcode; // caps
	@Column(name = "clienttype", length = 30)
	private String clienttype;
	@Column(name = "fiofife")
	private int fifofife;
	@Column(unique = true)
	private String dupchk;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "customerid")
	private CustomerVO customerVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
