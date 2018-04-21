package com.marhaj.money.user;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.marhaj.money.account.dto.AccountNotFoundException;
import com.marhaj.money.user.dto.User;

class InMemoryUserRepository implements UserRepository {
	private static Map<String, User> map = new ConcurrentHashMap();
		
	public User save(User user) {
		Objects.requireNonNull(user);
		map.put(user.getName(), user);
		return user;
	}

	public User find(String name) {
		Objects.requireNonNull(name);
		return map.get(name);
	}

	public List<User> findAll() {
		return map.values().stream().map(x -> (User) x).collect(Collectors.toList());
	}

	public void delete(String name) {
		if (null == map.remove(name))
			throw new AccountNotFoundException(name);
	}
}
