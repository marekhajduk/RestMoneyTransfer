package com.marhaj.money.account;

import java.util.List;

import com.marhaj.money.account.dto.Account;

interface AccountRepository {
	Account save(Account account);
	Account find(String userName);
	List<Account> findAll();
	void delete(String userName);
}
