package com.marhaj.money.account;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marhaj.money.BaseEndpoint;
import com.marhaj.money.user.UserEndpointTest;
import com.marhaj.money.user.dto.User;

public class AccountEndpointTest extends BaseEndpoint {

	@BeforeClass
	public static void beforeAccount() throws ClientProtocolException, URISyntaxException, IOException {
		User userA = new User(userNameA, userEmail, userPhone);
		User userB = new User(userNameB, userEmail, userPhone);
		UserEndpointTest.saveEntity(userA);
		UserEndpointTest.saveEntity(userB);
	}

	@AfterClass
	public static void afterAccount() throws URISyntaxException, ClientProtocolException, IOException {
		deleteEntity("/user/" + userNameA);
		deleteEntity("/user/" + userNameB);
	}

	@Before
	public void before() throws URISyntaxException, ClientProtocolException, IOException {
		assertTrue(getAll(Account.class).size() == 0);
	}

	@After
	public void after() throws URISyntaxException, ClientProtocolException, IOException {
		deleteEntity("/account/" + userNameA);
		deleteEntity("/account/" + userNameB);
	}

	@Test
	public void saveAccountTest() throws URISyntaxException, ClientProtocolException, IOException {
		Account account = new Account(userNameA, balanceA);
		Account receiveAccount = saveEntity(account);
		assertTrue(receiveAccount.getUser().equals(userNameA));
		assertTrue(receiveAccount.getBalance().equals(balanceA));
	}

	@Test
	public void updateAccountTest() throws URISyntaxException, ClientProtocolException, IOException {
		Account account = new Account(userNameA, balanceA);
		saveEntity(account);

		Account updateAccount = new Account(userNameA, balanceB);
		saveEntity(updateAccount);

		List<Account> allAccounts = getAll(Account.class);
		assertTrue(allAccounts.size() == 1);
	}

	@Test
	public void readAccountsTest() throws URISyntaxException, ClientProtocolException, IOException {
		Account accountA = new Account(userNameA, balanceA);
		saveEntity(accountA);

		Account accountB = new Account(userNameB, balanceB);
		saveEntity(accountB);

		List<Account> allAccounts = getAll(Account.class);
		assertTrue(allAccounts.size() == 2);
		assertTrue(allAccounts.stream().filter(x -> x.getUser().equals(userNameA)).count() == 1);
		assertTrue(allAccounts.stream().filter(x -> x.getUser().equals(userNameB)).count() == 1);
	}

	@Test
	public void readAccountTest() throws URISyntaxException, ClientProtocolException, IOException {
		Account accountA = new Account(userNameA, balanceA);
		saveEntity(accountA);

		String entity = getEntity("/account/" + userNameA);
		Account reciveAccount = mapper.readValue(entity, Account.class);

		assertTrue(reciveAccount.getUser().equals(userNameA));
		assertTrue(reciveAccount.getBalance().equals(balanceA));
	}

	@Test
	public void deleteAccountTest() throws URISyntaxException, ClientProtocolException, IOException {
		Account accountA = new Account(userNameA, balanceA);
		saveEntity(accountA);
		Account accountB = new Account(userNameB, balanceB);
		saveEntity(accountB);

		deleteEntity("/account/" + userNameA);

		List<Account> allAccounts = getAll(Account.class);
		assertTrue(allAccounts.size() == 1);
		assertTrue(allAccounts.stream().filter(x -> x.getUser().equals(userNameB)).count() == 1);
	}
}
