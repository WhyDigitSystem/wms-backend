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
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@PrePersist
	public void onSave() {
		LocalDateTime currentDate = LocalDateTime.now();
		this.createdDate = currentDate;
		this.updatedDate = currentDate;
	}

	@PostLoad
	public void onUpdate() {
		LocalDateTime currentDate = LocalDateTime.now();
		this.updatedDate = currentDate;
	}

}
