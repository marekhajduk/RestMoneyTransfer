package com.marhaj.money.transfer.dto;

import java.math.BigDecimal;

import com.marhaj.money.account.dto.Account;

public class NotEnoughBalanceException extends RuntimeException {
	public NotEnoughBalanceException(Account account, BigDecimal amount) {
		super("Not enough user " + account.getUser() + " balance. Would like to transfer " + amount.toString()
				+ " , but have only " + account.getBalance().toString(), null, false, false);
	}

}
