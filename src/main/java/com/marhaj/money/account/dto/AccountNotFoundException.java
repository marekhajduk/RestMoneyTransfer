package com.marhaj.money.account.dto;

public class AccountNotFoundException extends RuntimeException{
	public AccountNotFoundException(String userName) {
		super("No account for user " + userName + " found",null,false,false);
	}
}
