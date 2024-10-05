package com.whydigit.wms.entity;

import java.time.LocalDate;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dekittingparent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeKittingParentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dekittingparentgen")
	@SequenceGenerator(name = "dekittingparentgen", sequenceName = "dekittingparentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dekittingparentid")
	private Long id;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "qty")
	private int qty;
	@Column(name = "qcflag",length =25)
	private String qcFlag;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "stokcdate")
	private LocalDate stockDate;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "dekittingid")
	private DeKittingVO deKittingVO;
}
