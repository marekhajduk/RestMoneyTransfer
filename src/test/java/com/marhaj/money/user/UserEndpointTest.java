package com.marhaj.money.user;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marhaj.money.BaseEndpoint;
import com.marhaj.money.user.dto.User;

public class UserEndpointTest extends BaseEndpoint {

	@Before
	public void before() throws URISyntaxException, ClientProtocolException, IOException {
		assertTrue(getAll(User.class).size() == 0);
	}

	@After
	public void after() throws URISyntaxException, ClientProtocolException, IOException {
		deleteEntity("/user/" + userNameA);
		deleteEntity("/user/" + userNameB);
	}

	@Test
	public void saveUserTest() throws URISyntaxException, ClientProtocolException, IOException {
		User user = new User(userNameA, userEmail, userPhone);
		User responseUser = saveEntity(user);
		assertTrue(responseUser.getName().equals(userNameA));
		assertTrue(responseUser.getEmail().equals(userEmail));
		assertTrue(responseUser.getPhone().equals(userPhone));
	}

	@Test
	public void updateUserTest() throws URISyntaxException, ClientProtocolException, IOException {
		User user = new User(userNameA, userEmail, userPhone);
		saveEntity(user);

		User updateUser = new User(userNameA, userEmail, "87654321");
		saveEntity(updateUser);

		List<User> allUsers = getAll(User.class);
		assertTrue(allUsers.size() == 1);
	}

	@Test
	public void readUsersTest() throws URISyntaxException, ClientProtocolException, IOException {
		User user = new User(userNameA, userEmail, userPhone);
		saveEntity(user);

		User user2 = new User(userNameB, userEmail, userPhone);
		saveEntity(user2);

		List<User> allUsers = getAll(User.class);
		assertTrue(allUsers.size() == 2);
		assertTrue(allUsers.stream().filter(x -> x.getName().equals(userNameA)).count() == 1);
		assertTrue(allUsers.stream().filter(x -> x.getName().equals(userNameB)).count() == 1);
	}

	@Test
	public void readUserTest() throws URISyntaxException, ClientProtocolException, IOException {
		User user = new User(userNameA, userEmail, userPhone);
		saveEntity(user);

		String entity = getEntity("/user/" + userNameA);
		User reciveUser = mapper.readValue(entity, User.class);

		assertTrue(reciveUser.getName().equals(userNameA));
		assertTrue(reciveUser.getEmail().equals(userEmail));
		assertTrue(reciveUser.getPhone().equals(userPhone));
	}

	@Test
	public void deleteUserTest() throws URISyntaxException, ClientProtocolException, IOException {
		User user = new User(userNameA, userEmail, userPhone);
		saveEntity(user);

		String newUserName = "userB";
		User user2 = new User(newUserName, userEmail, userPhone);
		saveEntity(user2);

		deleteEntity("/user/" + userNameA);

		List<User> allUsers = getAll(User.class);
		assertTrue(allUsers.size() == 1);
		assertTrue(allUsers.stream().filter(x -> x.getName().equals(newUserName)).count() == 1);
	}
}
