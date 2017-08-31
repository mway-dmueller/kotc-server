package com.kotc.circuit.enumerable.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.circuit.enumerable.service.Enumerable;
import com.kotc.circuit.enumerable.service.EnumerableService;
import com.kotc.circuit.enumerable.util.EnumerableValue;
import com.kotc.circuit.enumerable.util.PaginationUtils;
import com.kotc.db.util.Query;

@Component
@EnumerableValue("enumerables")
public class EnumerableServiceImpl implements EnumerableService, Enumerable {

	private final ConcurrentHashMap<String, Enumerable> enumerables = new ConcurrentHashMap<>();

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

		final Enumerable enumService = enumerables.get(enumerable);
		if (enumService == null) {
			return null;
		}

		return enumService.getKeyValuePairs(query);
	}

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final Query query) {

		final List<KeyValuePair> keyValuePairs = new ArrayList<>();
		for (final String enumerableName : enumerables.keySet()) {
			keyValuePairs.add(new KeyValuePair(enumerableName));
		}

		return PaginationUtils.paginate(keyValuePairs, query.getPageable());
	}
}
