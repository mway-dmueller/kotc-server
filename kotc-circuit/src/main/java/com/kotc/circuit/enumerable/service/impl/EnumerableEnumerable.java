package com.kotc.circuit.enumerable.service.impl;

import java.util.ArrayList;
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
public class EnumerableEnumerable implements Enumerable {

	@Autowired
	private EnumerableService enumerableService;

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final Query query) {
		final List<KeyValuePair> keyValuePairs = new ArrayList<>();

		final ConcurrentHashMap<String, Enumerable> enumerables = enumerableService.getEnumerables();
		for (final String enumerableName : enumerables.keySet()) {
			keyValuePairs.add(new KeyValuePair(enumerableName));
		}

		return PaginationUtils.paginate(keyValuePairs, query.getPageable());
	}
}
