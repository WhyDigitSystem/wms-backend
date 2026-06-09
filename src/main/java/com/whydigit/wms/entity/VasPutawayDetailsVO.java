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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vasputawaydetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VasPutawayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vasputawaydetailsgen")
	@SequenceGenerator(name = "vasputawaydetailsgen", sequenceName = "vasputawaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vasputawaydetailsid")
	private Long id;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "status",length =10)
	private String status ="R";
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "putawayqty")
	private int putAwayQty;
	@Column(name = "frombin",length =25)
	private String fromBin;
	@Column(name = "tobin",length =25)
	private String toBin;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "qcflag",length =10)
	private String qcFlag;
	@Column(name = "frombinclass",length =25)
	private String fromBinClass;
	@Column(name = "frombintype",length =25)
	private String fromBinType;
	@Column(name = "fromcelltype",length =25)
	private String fromCellType;
	@Column(name = "fromcore",length =25)
	private String fromCore;	
	@Column(name = "tobinclass",length =25)
	private String toBinClass;
	@Column(name = "tobintype",length =25)
	private String toBinType;
	@Column(name = "tocelltype",length =25)
	private String toCellType;
	@Column(name = "tocore",length =25)
	private String toCore;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "vasputawayid")
	private VasPutawayVO vasPutawayVO;
}
