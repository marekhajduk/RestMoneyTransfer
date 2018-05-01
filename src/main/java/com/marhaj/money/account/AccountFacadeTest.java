package com.marhaj.money.account;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.marhaj.money.user.UserFacade;
import com.marhaj.money.user.dto.User;

public class AccountFacadeTest {
	private static final String userName = "userA";
	private static final String userPhone = "1234567689";
	private static final String userEmail = "userA@userA.com";
	
	private AccountFacade accountFacade;
	private UserFacade userFacade;
	
	@Before
	public void setup() {
		userFacade = new UserFacade();
		userFacade.save(new User(userName, userEmail, userPhone));
		accountFacade = new AccountFacade();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
