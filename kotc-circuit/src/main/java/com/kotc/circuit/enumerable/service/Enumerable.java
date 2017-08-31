package com.kotc.circuit.enumerable.service;

import org.springframework.data.domain.Page;

import com.kotc.circuit.enumerable.domain.KeyValuePair;
import com.kotc.db.util.Query;

public interface Enumerable {

	Page<KeyValuePair> getKeyValuePairs(Query query);
}
