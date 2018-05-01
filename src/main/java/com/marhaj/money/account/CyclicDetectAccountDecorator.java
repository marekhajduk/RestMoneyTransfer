package com.marhaj.money.account;

import java.math.BigDecimal;
import java.util.Objects;

import com.google.common.util.concurrent.CycleDetectingLockFactory;
import com.google.common.util.concurrent.CycleDetectingLockFactory.Policies;

//  Skip of using due to race condition in checkAcquiredLock of LockGraphNode class.
class CyclicDetectAccountDecorator implements AccountDecorator {
	private static CycleDetectingLockFactory factory = CycleDetectingLockFactory.newInstance(Policies.THROW);

	@Override
	public Account decorate(Account account) {
		final String user = Objects.requireNonNull(account.getUser());
		final BigDecimal balance = Objects.requireNonNull(account.getBalance());

		return (null != account.getLock()) ? account : new Account(factory.newReentrantLock(user), user, balance);
	}
}
