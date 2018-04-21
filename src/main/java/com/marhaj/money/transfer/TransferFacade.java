package com.marhaj.money.transfer;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.List;

import com.marhaj.money.account.AccountFacade;
import com.marhaj.money.account.dto.Account;
import com.marhaj.money.transfer.dto.NotEnoughBalanceException;
import com.marhaj.money.transfer.dto.Transfer;

public class TransferFacade {
	private TransferRepository trasferRepository;
	private AccountFacade accountFacade;

	public TransferFacade() {
		this(new InMemoryTransferRepository(), new AccountFacade());
	}
	
	public TransferFacade(TransferRepository transferRepository) {
		this(transferRepository, new AccountFacade());
	}
	
	public TransferFacade(TransferRepository trasferRepository, AccountFacade accountFacade) {
		this.trasferRepository = trasferRepository;
		this.accountFacade = accountFacade;
	}
	
	public Transfer save(Transfer transfer) {
		requireNonNull(transfer);
		Account from = requireNonNull(accountFacade.account(transfer.getFrom()));
		Account to   = requireNonNull(accountFacade.account(transfer.getTo()));
		
		BigDecimal amount = transfer.getAmount();
		BigDecimal fromBalance = from.getBalance();
		BigDecimal toBalance = to.getBalance();
		
		BigDecimal fromNewBalance = fromBalance.subtract(amount);
		
		if(fromNewBalance.signum() < 0) {
			throw new NotEnoughBalanceException(from, amount);
		}
		
		from.setBalance(fromNewBalance);
		to.setBalance(toBalance.add(amount));
		accountFacade.save(from);
		accountFacade.save(to);
		trasferRepository.save(transfer);
		return transfer;
	}
	
	public List<Transfer> transfers() {
		return trasferRepository.findAll();
	}
}
