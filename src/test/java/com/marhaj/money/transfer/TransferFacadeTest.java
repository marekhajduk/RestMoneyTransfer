package com.marhaj.money.transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.marhaj.money.account.Account;
import com.marhaj.money.account.AccountFacade;
import com.marhaj.money.transfer.dto.Transfer;
import com.marhaj.money.user.UserFacade;
import com.marhaj.money.user.dto.User;

public class TransferFacadeTest {
	private static final String nameA 		= "userA";
	private static final String nameB 		= "userB";
	private static final String nameC 		= "userC";
	private static final String emailA 		= nameA + "@user.com";
	private static final String emailB 		= nameB + "@user.com";
	private static final String emailC 		= nameB + "@user.com";
	private static final BigDecimal amountA = new BigDecimal("40000");
	private static final BigDecimal amountB = new BigDecimal("30000");
	private static final BigDecimal amountC = new BigDecimal("20000");

	private UserFacade userFacade;
	private AccountFacade accountFacade;
	private TransferFacade transferFacade;
	private Account accountA,accountB,accountC;
	private User userA,userB,userC;

	@Before
	public void before() {
		userA = new User(nameA, emailA, "123");
		userB = new User(nameB, emailB, "876");
		userC = new User(nameC, emailC, "456");
		accountA = new Account(nameA, amountA);
		accountB = new Account(nameB, amountB);
		accountC = new Account(nameC, amountC);
		userFacade = new UserFacade();
		userFacade.save(userA);
		userFacade.save(userB);
		userFacade.save(userC);
		accountFacade = new AccountFacade(userFacade);
		accountFacade.save(accountA);
		accountFacade.save(accountB);
		accountFacade.save(accountC);
		transferFacade = new TransferFacade(accountFacade);
	}
	
	@After
	public void after() {
		transferFacade.delete();
		accountFacade.delete(nameA, nameB, nameC);
		userFacade.delete(nameA, nameB, nameC);
	}

	@Test
	public void shouldCompleteManyTransation() throws InterruptedException {
		int count = 200;
		CountDownLatch latch = new CountDownLatch(1);

		Transfer transferA = new Transfer(nameA, nameB, new BigDecimal(1));
		Transfer transferB = new Transfer(nameB, nameC, new BigDecimal(1));
		Transfer transferC = new Transfer(nameC, nameA, new BigDecimal(1));
		
		List<Thread> aThreads = listOfThreads(count, transferA, latch);
		List<Thread> bThreads = listOfThreads(count, transferB, latch);
		List<Thread> cThreads = listOfThreads(count, transferC, latch);
		
		aThreads.forEach(Thread::start);
		bThreads.forEach(Thread::start);
		cThreads.forEach(Thread::start);
		
		latch.countDown();
		
		for(Thread thread : aThreads) 	thread.join();
		for(Thread thread : bThreads)	thread.join();
		for(Thread thread : cThreads)	thread.join();
		
		Assert.assertTrue(accountFacade.account(nameA).getBalance().equals(amountA));
		Assert.assertTrue(accountFacade.account(nameB).getBalance().equals(amountB));
		Assert.assertTrue(accountFacade.account(nameC).getBalance().equals(amountC));
		Assert.assertTrue(transferFacade.transfers().size()==120000);
	}
	
	@Test
	public void shouldCompleteAllCyclicTransaction() throws InterruptedException, ExecutionException {
		int count = 200;
		CountDownLatch latch = new CountDownLatch(1);

		Transfer original = new Transfer(nameA, nameB, new BigDecimal(1));
		Transfer reverse= new Transfer(nameB, nameA, new BigDecimal(1));
		
		List<Thread> originalThreads = listOfThreads(count, original, latch);
		List<Thread> reverseThreads = listOfThreads(count, reverse, latch);
		
		originalThreads.forEach(Thread::start);
		reverseThreads.forEach(Thread::start);
		
		latch.countDown();
		
		for(Thread thread : originalThreads) 	thread.join();
		for(Thread thread : reverseThreads)		thread.join();
		
		Assert.assertTrue(accountFacade.account(nameA).getBalance().equals(amountA));
		Assert.assertTrue(accountFacade.account(nameB).getBalance().equals(amountB));
		Assert.assertTrue(transferFacade.transfers().size()==80000);
	}

	private List<Thread> listOfThreads(int count, Transfer transfer, CountDownLatch latch) {
		List<Thread> originalThreads = Stream.iterate(0, i-> i+1).limit(count)
				.map(i-> new Thread(() ->  {
					try {
						latch.await();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					for(int j = 0 ; j <count ; j++) {
						transferFacade.save(transfer);
					}
				})).collect(Collectors.toList());
		return originalThreads;
	}
}
