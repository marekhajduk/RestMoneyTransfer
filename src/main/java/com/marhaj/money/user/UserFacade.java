package com.marhaj.money.user;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.marhaj.money.user.dto.User;

public class UserFacade {
	private UserRepository userRepository; 
	
	public UserFacade() {
		this(new InMemoryUserRepository());
	}
	
	public UserFacade(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User save(User user) {
		Objects.requireNonNull(user);
		return userRepository.save(user);
	}
	
	public void delete(String ...names) {
		Objects.requireNonNull(names);
		Arrays.stream(names).forEach(userRepository::delete);
	}
	
	public User user(String name) {
		Objects.requireNonNull(name);
		return userRepository.find(name);
	}
	
	public List<User> users() {
		return userRepository.findAll();
	}
}
