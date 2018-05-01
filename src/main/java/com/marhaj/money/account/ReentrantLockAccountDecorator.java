package com.marhaj.money.account;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAccountDecorator implements AccountDecorator{

	@Override
	public Account decorate(Account account) {
		final String user = Objects.requireNonNull(account.getUser());
		final BigDecimal balance = Objects.requireNonNull(account.getBalance());

		return (null != account.getLock()) ? account : new Account(new ReentrantLock(), user, balance);
	}
}
