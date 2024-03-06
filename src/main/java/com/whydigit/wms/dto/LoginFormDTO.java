package com.whydigit.wms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormDTO {

	@NotBlank
	@Size(max = 30)
	private String userName;

	@NotBlank
	@Size(min = 6, max = 100, message = "Password is required")
	private String password;
}
