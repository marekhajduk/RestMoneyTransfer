package com.marhaj.money.account;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryAccountRepository implements AccountRepository {
	private static Map<String, Account> map = new ConcurrentHashMap();

	public Account save(Account account) {
		Objects.requireNonNull(account);
		map.put(account.getUser(), account);
		return account;
	}

	public Account find(String userName) {
		Objects.requireNonNull(userName);
		return map.get(userName);
	}

	public List<Account> findAll() {
		return map.values().stream().map(x -> (Account) x).collect(Collectors.toList());
	}

	public void delete(String userName) {
		Objects.requireNonNull(userName);
		map.remove(userName);
	}
}
