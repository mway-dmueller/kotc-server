package com.kotc.circuit.enumerable.service.impl;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.circuit.enumerable.service.Enumerable;
import com.kotc.circuit.enumerable.service.EnumerableService;
import com.kotc.circuit.enumerable.util.EnumerableValue;
import com.kotc.db.util.Query;

@Component
public class EnumerableServiceImpl implements EnumerableService {

	private final ConcurrentHashMap<String, Enumerable> enumerables = new ConcurrentHashMap<>();

	public ConcurrentHashMap<String, Enumerable> getEnumerables() {
		return enumerables;
	}

	@Autowired
	public void setEnumerables(final Enumerable[] enumerables) {
		Arrays.stream(enumerables).forEach(enumerable -> {
			final EnumerableValue enumerableValue = enumerable.getClass().getDeclaredAnnotation(EnumerableValue.class);
			if (enumerableValue != null) {
				this.enumerables.put(enumerableValue.value(), enumerable);
			}
		});
	}

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final String enumerable, final Query query) {

		final Enumerable enumerableService = enumerables.get(enumerable);
		if (enumerableService == null) {
			return null;
		}

		return enumerableService.getKeyValuePairs(query);
	}
}
