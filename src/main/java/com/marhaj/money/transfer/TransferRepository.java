package com.marhaj.money.transfer;

import java.util.List;

import com.marhaj.money.transfer.dto.Transfer;

interface TransferRepository {
	Transfer save(Transfer transfer);
	List<Transfer> findAll();
}
