package com.marhaj.money.transfer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.marhaj.money.transfer.dto.Transfer;

class InMemoryTransferRepository implements TransferRepository {
	private static Map<Long, Transfer> map = new ConcurrentHashMap<>();
	private static AtomicLong id = new AtomicLong();

	public Transfer save(Transfer transfer) {
		map.put(id.incrementAndGet(), transfer);
		return transfer;
	}

	public List<Transfer> findAll() {
		return map.values().stream().map(x -> (Transfer) x).collect(Collectors.toList());
	}

	public void delete() {
		map.clear();
	}
}
