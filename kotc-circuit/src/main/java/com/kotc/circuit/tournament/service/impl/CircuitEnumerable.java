package com.kotc.circuit.tournament.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.circuit.enumerable.service.Enumerable;
import com.kotc.circuit.enumerable.util.EnumerableValue;
import com.kotc.db.util.Query;

@Component
@EnumerableValue(value = "circuits")
public class CircuitEnumerable implements Enumerable {

	@Override
	public Page<KeyValuePair> getKeyValuePairs(final Query query) {
		// TODO Auto-generated method stub
		return null;
	}
}
