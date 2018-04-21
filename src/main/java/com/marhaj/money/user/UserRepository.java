package com.marhaj.money.user;

import java.util.List;

import com.marhaj.money.user.dto.User;

interface UserRepository {
	User save(User user);
	User find(String name);
	List<User> findAll();
	void delete(String name);
}
