package com.marhaj.money.account;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor(access=AccessLevel.PACKAGE)
@NoArgsConstructor(access=AccessLevel.PACKAGE)
public class Account {
	@JsonIgnore
	@Setter(AccessLevel.NONE)
	private ReentrantLock lock;
	
	@JsonProperty(required=true)
	private String user;
	
	@JsonProperty(required=true)
	private BigDecimal balance;

	public Account(String user, BigDecimal balance) {
		super();
		this.user = user;
		this.balance = balance;
	}
}
