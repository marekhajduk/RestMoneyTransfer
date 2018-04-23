package com.marhaj.money.account.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@JsonProperty(required=true)
	private String user;
	
	@JsonProperty(required=true)
	private BigDecimal balance;
}
