package com.marhaj.money.account;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import com.marhaj.money.account.dto.Account;
import com.marhaj.money.user.UserFacade;

public class AccountFacade {
	private AccountRepository accountRepository;
	private UserFacade userFacade;

	public AccountFacade() {
		this(new InMemoryAccountRepository(), new UserFacade());
	}

	public AccountFacade(AccountRepository accountRepository) {
		this(accountRepository, new UserFacade());
	}

	public AccountFacade(AccountRepository accountRepository, UserFacade userFacade) {
		this.accountRepository = accountRepository;
		this.userFacade = userFacade;
	}

	public Account save(Account account) {
		requireNonNull(account);
		requireNonNull(userFacade.user(account.getUser()));
		return accountRepository.save(account);
	}

	public void delete(String... names) {
		requireNonNull(names);
		Arrays.stream(names).forEach(accountRepository::delete);
	}

	public Account account(String userName) {
		requireNonNull(userName);
		return accountRepository.find(userName);
	}

	public List<Account> accounts() {
		return accountRepository.findAll();
	}
}
