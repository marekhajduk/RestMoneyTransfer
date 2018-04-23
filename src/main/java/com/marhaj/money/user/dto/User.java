package com.marhaj.money.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@JsonProperty(required=true)
	private String name;
	
	@JsonProperty(required=true)
	private String email;
	
	@JsonProperty(required=true)
	private String phone;
}
