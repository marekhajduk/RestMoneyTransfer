package com.marhaj.money.account;

import java.util.List;

interface AccountRepository {
	Account save(Account account);
	Account find(String userName);
	List<Account> findAll();
	void delete(String userName);
}
