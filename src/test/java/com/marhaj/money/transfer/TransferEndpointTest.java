package com.marhaj.money.transfer;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marhaj.money.BaseEndpoint;
import com.marhaj.money.account.Account;
import com.marhaj.money.account.AccountEndpointTest;
import com.marhaj.money.transfer.dto.Transfer;
import com.marhaj.money.user.UserEndpointTest;
import com.marhaj.money.user.dto.User;

public class TransferEndpointTest extends BaseEndpoint {

	@BeforeClass
	public static void beforeTransfer() throws ClientProtocolException, URISyntaxException, IOException {
		User userA = new User(userNameA, userEmail, userPhone);
		User userB = new User(userNameB, userEmail, userPhone);
		UserEndpointTest.saveEntity(userA);
		UserEndpointTest.saveEntity(userB);
		Account accountA = new Account(userNameA, balanceA);
		Account accountB = new Account(userNameB, balanceB);
		AccountEndpointTest.saveEntity(accountA);
		AccountEndpointTest.saveEntity(accountB);
	}

	@AfterClass
	public static void afterTransfer() throws URISyntaxException, ClientProtocolException, IOException {
		deleteEntity("/account/" + userNameA);
		deleteEntity("/account/" + userNameB);
		deleteEntity("/user/" + userNameA);
		deleteEntity("/user/" + userNameB);
		deleteEntity("/transfer");
	}

	@Test
	public void saveTransferTest() throws URISyntaxException, ClientProtocolException, IOException {
		Transfer transferA = new Transfer(userNameA, userNameB, new BigDecimal(20));
		Transfer transferB = new Transfer(userNameA, userNameB, new BigDecimal(40));
		saveEntity(transferA);
		saveEntity(transferB);

		String entityA = getEntity("/account/" + userNameA);
		Account accountA = mapper.readValue(entityA, Account.class);

		String entityB = getEntity("/account/" + userNameB);
		Account accountB = mapper.readValue(entityB, Account.class);

		assertTrue(accountA.getBalance().equals(new BigDecimal(40)));
		assertTrue(accountB.getBalance().equals(new BigDecimal(260)));
		
		List<Transfer> allTransfer = getAll(Transfer.class);
		assertTrue(allTransfer.size()==2);
	}
}
