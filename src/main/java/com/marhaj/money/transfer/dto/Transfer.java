package com.marhaj.money.transfer.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transfer {
	@JsonProperty(required = true)
	private String from;

	@JsonProperty(required = true)
	private String to;

	@JsonProperty(required = true)
	private BigDecimal amount;
}
