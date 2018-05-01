package com.marhaj.money.transfer.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PACKAGE)
public class Transfer {
	@JsonProperty(required = true)
	private String from;

	@JsonProperty(required = true)
	private String to;

	@JsonProperty(required = true)
	private BigDecimal amount;
}
