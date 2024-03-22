package com.whydigit.wms.dto;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUpdatedDate {
	private LocalDateTime createdon;
	private LocalDateTime modifiedon;

	@PrePersist
	public void onSave() {
		LocalDateTime currentDate = LocalDateTime.now();
		this.createdon = currentDate;
		this.modifiedon = currentDate;
	}

	@PostLoad
	public void onUpdate() {
		LocalDateTime currentDate = LocalDateTime.now();
		this.modifiedon = currentDate;
	}

}
