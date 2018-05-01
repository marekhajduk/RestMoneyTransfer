package com.marhaj.money.transfer.dto;

import net.jodah.failsafe.RetryPolicy;

public class TransferRetryPolicyException extends RuntimeException {
	public TransferRetryPolicyException(Transfer transfer, RetryPolicy policy) {
		super("Failure transfer from " + transfer.getFrom() + "  to " + transfer.getTo() + " , amount "
				+ transfer.getAmount() + " after " + policy.getMaxRetries() + " retries." , null, false, false);
	}
}
