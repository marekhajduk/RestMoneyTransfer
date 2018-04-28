package com.marhaj.money.account;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

import com.marhaj.money.user.UserFacade;

public class AccountFacade {
	private AccountDecorator accountDecorator;
	private AccountRepository accountRepository;
	private UserFacade userFacade;

	public AccountFacade() {
		this(new CyclicDetectAccountDecorator(), new InMemoryAccountRepository(), new UserFacade());
	}

	public AccountFacade(AccountDecorator accountDecorator, AccountRepository accountRepository) {
		this(accountDecorator, accountRepository, new UserFacade());
	}

	public AccountFacade(AccountDecorator accountDecorator, AccountRepository accountRepository,
			UserFacade userFacade) {
		this.accountDecorator = accountDecorator;
		this.accountRepository = accountRepository;
		this.userFacade = userFacade;
	}

	public Account save(Account account) {
		requireNonNull(account);
		requireNonNull(userFacade.user(account.getUser()));
		Account decorateAccount = accountDecorator.decorate(account);
		Lock lock = decorateAccount.getLock();
		lock.lock();
		accountRepository.save(decorateAccount);
		lock.unlock();
		return decorateAccount;
	}

	public void delete(String... names) {
		requireNonNull(names);
		Arrays.stream(names).map(name -> account(name)).parallel().forEach(account -> {
			Lock lock = account.getLock();
			accountRepository.delete(account.getUser());
			lock.unlock();
		});
	}

	public Account account(String userName) {
		requireNonNull(userName);
		return accountRepository.find(userName);
	}

	public List<Account> accounts() {
		return accountRepository.findAll();
	}
}
