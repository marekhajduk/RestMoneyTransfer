package com.marhaj.money.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User {
	@JsonProperty(required=true)
	private String name;
	
	@JsonProperty(required=true)
	private String email;
	
	@JsonProperty(required=true)
	private String phone;
}
